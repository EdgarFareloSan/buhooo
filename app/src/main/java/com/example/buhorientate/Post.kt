package com.example.buhorientate
import com.google.firebase.firestore.Exclude


class Post(val post: String? = null, val ServiceName: String? = null, val likes: ArrayList<String>?= arrayListOf()) {
    @Exclude
    @set:Exclude
    @get:Exclude
    var uid: String? = null

    constructor():this(null, null, null)

}