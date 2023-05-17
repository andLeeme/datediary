package com.project.datediary.model


data class HTTP_GET_Model(
    var something : String? =null ,
    var users : ArrayList<UserModel>? =null
)


data class UserModel(
    var idx : Int? =null ,
    var id : String?=null,
    var nick : String? =null
)


data class PostModel(
    var id : String? =null ,
    var pwd : String?=null,
    var nick : String? =null
)

data class PostResult(
    var result:String? = null
)