package com.achizhikov.kotlinutilitiesbot.telegram

enum class State {
    NOT_STARTED,
    MAIN,
    GET_DATA,
    INPUT_DATA,

    // INPUT UTILITIES DATA
    INPUT_UD_DATE,
    INPUT_UD_ELECTRICITY,
    INPUT_UD_BHW,
    INPUT_UD_BCW,
    INPUT_UD_KHW,
    INPUT_UD_KCW,

    // INPUT TARIFF
    INPUT_T_DATE,
    INPUT_T_ELECTRICITY,
    INPUT_T_HW,
    INPUT_T_CW,
    INPUT_T_DRAINAGE

}