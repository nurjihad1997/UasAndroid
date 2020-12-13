package com.example.uasandoid

class data_penduduk {
    var nokk:String? = null
    var namakepala:String? = null
    var alamat:String? = null
    var rt:String? = null
    var lurah:String? = null
    var camat:String? = null
    var kota:String? = null
    var provinsi:String? = null
    var key:String? = null

    constructor()

    constructor(nokk: String?, namakepala: String?, alamat: String?, rt: String?, lurah: String?, camat: String?, kota: String?, provinsi: String?) {
        this.nokk = nokk
        this.namakepala = namakepala
        this.alamat= alamat
        this.rt=rt
        this.lurah=lurah
        this.camat=camat
        this.kota=kota
        this.provinsi=provinsi
    }
}