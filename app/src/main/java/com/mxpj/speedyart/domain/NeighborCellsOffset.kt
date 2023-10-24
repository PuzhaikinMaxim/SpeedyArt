package com.mxpj.speedyart.domain

val offsetFor16x16 = listOf(
    Pair(0,-1), Pair(-1,0), Pair(1,0), Pair(0,1)
)

val offsetFor32x32 = listOf(
                    Pair(0,-2),
        Pair(-1,-1),Pair(0,-1),Pair(1,-1),
    Pair(-2,0),Pair(-1,0),Pair(1,0),Pair(2,0),
        Pair(-1,1),Pair(0,1),Pair(1,1),
                   Pair(0,2)
)