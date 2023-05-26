package com.project.datediary.model

object Constants {
    fun getPlaceData():ArrayList<Place>{
        // create an arraylist of type employee class
        val placeList=ArrayList<Place>()
        val emp1=Place("유정","")
        placeList.add(emp1)
        val emp2=Place("Ram prakash","ramp@gmail.com")
        placeList.add(emp2)
        val emp3=Place("OMM Meheta","mehetaom@gmail.com")
        placeList.add(emp3)
        val emp4=Place("Hari Mohapatra","harim@gmail.com")
        placeList.add(emp4)
        val emp5=Place("Abhisek Mishra","mishraabhi@gmail.com")
        placeList.add(emp5)
        val emp6=Place("Sindhu Malhotra","sindhu@gmail.com")
        placeList.add(emp6)
        val emp7=Place("Anil sidhu","sidhuanil@gmail.com")
        placeList.add(emp7)
        val emp8=Place("Sachin sinha","sinhas@gmail.com")
        placeList.add(emp8)
        val emp9=Place("Amit sahoo","sahooamit@gmail.com")
        placeList.add(emp9)
        val emp10=Place("Raj kumar","kumarraj@gmail.com")
        placeList.add(emp10)

        return placeList
    }
}
