package com.example.storewithcompose.di

import com.example.storewithcompose.MyApp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import javax.inject.Singleton

@Module
@InstallIn(MyApp::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirestoreCollectionReference(): CollectionReference {
        return FirebaseFirestore.getInstance().collection("productsCollection")
    }

}