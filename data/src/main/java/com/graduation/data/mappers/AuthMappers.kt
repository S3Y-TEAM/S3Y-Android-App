package com.graduation.data.mappers

import com.graduation.data.remote.dto.auth.login.AuthResponseDto
import com.graduation.data.remote.dto.auth.login.CountryDto
import com.graduation.data.remote.dto.auth.login.UserDto
import com.graduation.domain.models.auth.Auth.AuthData
import com.graduation.domain.models.auth.Auth.AuthResponse
import com.graduation.domain.models.auth.Auth.Country
import com.graduation.domain.models.auth.Auth.User


fun AuthResponseDto?.toDomain(): AuthResponse {
    return AuthResponse(
        data = AuthData(
            type = this?.data?.type ?: "",
            user = this?.data?.user?.toDomain() ?: User()
        )
    )
}

fun UserDto.toDomain(): User {
    return User(
        address = address ?: "",
        country = country?.toDomain(),
        id = id,
        image = image,
        isVerified = isVerified,
        language = language,
        name = name,
        phone = phone,
        token = token
    )
}

fun CountryDto.toDomain(): Country {
    return Country(
        code = code,
        currency = currency,
        flag = flag,
        maxNumber = maxNumber,
        name = name
    )
}