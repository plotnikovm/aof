package io.aof.db

class Fap(
    val id: Long,
    val timestamp: Long,
    val rating: Long,
    val time: Long
)

class ListFap(
    val list: List<Fap>?
)
