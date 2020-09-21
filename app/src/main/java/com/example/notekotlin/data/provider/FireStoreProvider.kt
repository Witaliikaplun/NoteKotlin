package com.example.notekotlin.data.provider

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notekotlin.data.errors.NoAuthException
import com.example.notekotlin.data.model.Note
import com.example.notekotlin.data.model.Result
import com.example.notekotlin.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*



class FireStoreProvider(val store: FirebaseFirestore, val auth: FirebaseAuth) : RemoteDataProvider{

    companion object {
        private const val NOTES_COLLECTION = "notes"
        private const val USER_COLLECTION = "users"

    }

    private val currentUser
        get() = auth.currentUser

    val userNotesCollection
        get() = currentUser?.let { store.collection(USER_COLLECTION).document(it.uid).collection(NOTES_COLLECTION) } ?: throw NoAuthException()

    override fun getCurrentUser(): LiveData<User?> = MutableLiveData<User?>().apply {
        value = currentUser?.let { User(it.displayName ?: "", it.email ?: "") }
    }

    override fun subscribeToAllNotes(): LiveData<Result> = MutableLiveData<Result>().apply {
        try {
            userNotesCollection.addSnapshotListener { snapshot, e ->
                e?.let {
                    value = Result.Error(e)
                } ?: let {
                    snapshot?.let {
                        val notes = snapshot.documents.map { doc ->
                            doc.toObject(Note::class.java)
                        }
                        value = Result.Success(notes)
                    }
                }
            }
        } catch (e: Throwable) {
            value = Result.Error(e)
        }
    }

    override fun getNoteById(id: String): LiveData<Result> = MutableLiveData<Result>().apply {
        try {
            userNotesCollection.document(id).get()
                    .addOnSuccessListener { snapshot ->
                        value = Result.Success(snapshot.toObject(Note::class.java))
                    }.addOnFailureListener {
                        value = Result.Error(it)
                    }
        } catch (e: Throwable) {
            value = Result.Error(e)
        }
    }

    override fun saveNote(note: Note): LiveData<Result> = MutableLiveData<Result>().apply {
        try {
            userNotesCollection.document(note.id).set(note)
                    .addOnSuccessListener {
                        value = Result.Success(note)
                    }.addOnFailureListener {
                        value = Result.Error(it)
                    }
        } catch (e: Throwable) {
            value = Result.Error(e)
        }
    }

    override fun deleteNote(noteId: String): LiveData<Result> = MutableLiveData<Result>().apply {
        try {
            userNotesCollection.document(noteId).delete()
                    .addOnSuccessListener { snapshot ->
                        value = Result.Success(null)
                    }.addOnFailureListener {
                        value = Result.Error(it)
                    }
        } catch (e: Throwable) {
            value = Result.Error(e)
        }
    }

}

