package com.example.regolo.utils

class Constants private constructor(){

    companion object {
        //Camera
        const val PICK_PHOTO_REQUEST = 2
        const val TAKE_PHOTO_REQUEST = 1

        // Launch Screen
        const val DELAY_START_SCREEN = 1500

        // Maps
        const val TIME_UPDATE_LOCALIZATION = 10000L
        const val TIME_UPDATE_LOCALIZATION_FAST = 2000L
        const val ZOOM_CAMERA = 17f

        //Database Users
        const val USERS_TABLE_NAME: String = "UserTable"
        const val DATABASE_NAME: String = "db"
        const val NOME: String = "item1"
        const val COGNOME: String = "item2"
        const val E_MAIL: String = "item3"
        const val PASSWORD: String = "item4"
        const val DATA_DI_NASCITA: String = "item5"
        const val TIPO_REGISTRAZIONE: String = "item6"
        const val CODICE_RECUPERO: String = "item7"
        const val DATA_REGISTRAZIONE: String = "item8"

        //Database Locals
        const val LOCALS_TABLE_NAME: String = "LocalsTable"
        const val ADESIVO: String = "Adesivo"
        const val CAP: String = "Cap"
        const val CAT_ID: String = "Cat_id"
        const val CITTA: String = "Città"
        const val CIVICO: String = "Civico"
        const val CONFIRMED: String = "Confirmed"
        const val FACEBOOK: String = "Facebook"
        const val INSTAGRAM: String = "Instagram"
        const val KEYHASH: String = "Keyhash"
        const val LAT: String = "Lat"
        const val LON: String = "Lon"
        const val MAIL: String = "Mail"
        const val NAZIONE: String = "Nazione"
        const val NOME_LOCALE: String = "Nome_locale"
        const val PERCORSO_FOTO_LOCALE: String = "Percorso_foto_locale"
        const val PORTATE: String = "Portate"
        const val PRENOTAZIONE: String = "Prenotazione"
        const val PREZZO: String = "Prezzo"
        const val PROVINCIA: String = "Provincia"
        const val TELEFONO: String = "Telefono"
        const val TWITTER: String = "Twitter"
        const val VIA_LOCALE: String = "Via_locale"
        const val WEBSITE: String = "Website"

        //Database Allergie
        const val ALLERGIE_TABLE_NAME: String = "AllergieTable"
        const val ID_ALLERGIA: String = "id_allergia"
        const val NAME_ALLERGIA: String = "name_allergia"
        const val ID_LOCALE: String = "id_locale"


        //foto
        const val PERCORSO_FOTO: String = "PercorsoFoto"

        //password Rule
        const val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[@#$%^&+=!£])(?=\\S+$).{4,}$"

        //URL SERVER
        const val URL = "https://www.sliceat.it/WebService/"
        const val POST_USER_REQUEST = "inseriscoUtente.php"
    }
}
