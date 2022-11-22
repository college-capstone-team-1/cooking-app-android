package com.collegecapstoneteam1.cookingapp.data.model


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import retrofit2.http.GET


@Parcelize
@Entity(tableName = "recipes")
data class Recipe(
    val attFileNoMain: String,//이미지경로(소)
    val attFileNoMk: String,//이미지경로(대)
    val hashTag: String,//해쉬태그
    val id: Int, //서버에서 임으로 할당
    val infoCar: Double,//탄수화물
    val infoEng: Double,//열량
    val infoFat: Double,//지방
    val infoNa: Double,//나트륨
    val infoPro: Double,//단백질
    val infoWgt: Double,//중량(1인분)

    // MANUAL_TEXT
    val manual01: String,
    val manual02: String,
    val manual03: String,
    val manual04: String,
    val manual05: String,
    val manual06: String,
    val manual07: String,
    val manual08: String,
    val manual09: String,
    val manual10: String,
    val manual11: String,
    val manual12: String,
    val manual13: String,
    val manual14: String,
    val manual15: String,
    val manual16: String,
    val manual17: String,
    val manual18: String,
    val manual19: String,
    val manual20: String,

    // MANUAL_IMG
    val manualImg01: String,
    val manualImg02: String,
    val manualImg03: String,
    val manualImg04: String,
    val manualImg05: String,
    val manualImg06: String,
    val manualImg07: String,
    val manualImg08: String,
    val manualImg09: String,
    val manualImg10: String,
    val manualImg11: String,
    val manualImg12: String,
    val manualImg13: String,
    val manualImg14: String,
    val manualImg15: String,
    val manualImg16: String,
    val manualImg17: String,
    val manualImg18: String,
    val manualImg19: String,
    val manualImg20: String,

    val rcpNm: String,//메뉴명
    val rcpPartsDtls: String,//재료정보
    val rcpPat2: String,//요리종류

    @PrimaryKey(autoGenerate = false)
    val rcpSeq: Int,//api_일련번호

    val rcpWay2: String//조리방법
) : Parcelable {
    fun getDetailList() : List<Detail>{
        val size = getDetailSize()
        val list = mutableListOf<Detail>()

        if (size == 0){
            return list
        }

        list.add(Detail(manual01,manualImg01))
        if (size == 1){
            return list
        }

        list.add(Detail(manual02,manualImg02))
        if (size == 2){
            return list
        }

        list.add(Detail(manual03,manualImg03))
        if (size == 3){
            return list
        }

        list.add(Detail(manual04,manualImg04))
        if (size == 4){
            return list
        }

        list.add(Detail(manual05,manualImg05))
        if (size == 5){
            return list
        }

        list.add(Detail(manual06,manualImg06))
        if (size == 6){
            return list
        }

        list.add(Detail(manual07,manualImg07))
        if (size == 7){
            return list
        }

        list.add(Detail(manual08,manualImg08))
        if (size == 8){
            return list
        }

        list.add(Detail(manual09,manualImg09))
        if (size == 9){
            return list
        }

        list.add(Detail(manual10,manualImg10))
        if (size == 10){
            return list
        }

        list.add(Detail(manual11,manualImg11))
        if (size == 11){
            return list
        }

        list.add(Detail(manual12,manualImg12))
        if (size == 12){
            return list
        }

        list.add(Detail(manual13,manualImg13))
        if (size == 13){
            return list
        }

        list.add(Detail(manual14,manualImg14))
        if (size == 14){
            return list
        }

        list.add(Detail(manual15,manualImg15))
        if (size == 15){
            return list
        }

        list.add(Detail(manual16,manualImg16))
        if (size == 16){
            return list
        }

        list.add(Detail(manual17,manualImg17))
        if (size == 17){
            return list
        }

        list.add(Detail(manual18,manualImg18))
        if (size == 18){
            return list
        }


        list.add(Detail(manual19,manualImg19))
        if (size == 19){
            return list
        }

        list.add(Detail(manual20,manualImg20))
        return list
    }
    fun getDetailSize() : Int {
        if (manual01.isNullOrEmpty()){
            return 0
        } else if (manual02.isNullOrEmpty()){
            return 1
        } else if (manual03.isNullOrEmpty()){
            return 2
        } else if (manual04.isNullOrEmpty()){
            return 3
        } else if (manual05.isNullOrEmpty()){
            return 4
        } else if (manual06.isNullOrEmpty()){
            return 5
        } else if (manual07.isNullOrEmpty()){
            return 6
        } else if (manual08.isNullOrEmpty()){
            return 7
        } else if (manual09.isNullOrEmpty()){
            return 8
        } else if (manual10.isNullOrEmpty()){
            return 9
        } else if (manual11.isNullOrEmpty()){
            return 10
        } else if (manual12.isNullOrEmpty()){
            return 11
        } else if (manual13.isNullOrEmpty()){
            return 12
        } else if (manual14.isNullOrEmpty()){
            return 13
        } else if (manual15.isNullOrEmpty()){
            return 14
        } else if (manual16.isNullOrEmpty()){
            return 15
        } else if (manual17.isNullOrEmpty()){
            return 16
        } else if (manual18.isNullOrEmpty()){
            return 17
        } else if (manual19.isNullOrEmpty()){
            return 18
        } else if (manual20.isNullOrEmpty()){
            return 19
        } else{
            return 20
        }
    }
}