package com.example.madlevel2example

@Entity(tableName = "reminderTable")
data class Reminder(
    var reminderText: String
)