package com.company.makepub.app.domain;

public enum Book {

    BOOK_01_GEN("Gênesis",            "Gên.",    "Gên", new Integer[] {31, 25, 24, 26, 32, 22, 24, 22, 29, 32, 32, 20, 18, 24, 21, 16, 27, 33, 38, 18, 34, 24, 20, 67, 34, 35, 46, 22, 35, 43, 55, 32, 20, 31, 29, 43, 36, 30, 23, 23, 57, 38, 34, 34, 28, 34, 31, 22, 33, 26}),
    BOOK_02_EXO("Êxodo",              "Êxo.",    "Êx" , new Integer[] {22, 25, 22, 31, 23, 30, 25, 32, 35, 29, 10, 51, 22, 31, 27, 36, 16, 27, 25, 26, 36, 31, 33, 18, 40, 37, 21, 43, 46, 38, 18, 35, 23, 35, 35, 38, 29, 31, 43, 38}),
    BOOK_03_LEV("Levítico",           "Lev.",    "Le" , new Integer[] {17, 16, 17, 35, 19, 30, 38, 36, 24, 20, 47, 8,59, 57, 33, 34, 16, 30, 37, 27, 24, 33, 44, 23, 55, 46,34}),
    BOOK_04_NUM("Números",            "Núm.",    "Núm", new Integer[] {54, 34, 51, 49, 31, 27, 89, 26, 23, 36, 35, 16, 33, 45, 41, 50, 13, 32, 22, 29, 35, 41, 30, 25, 18, 65, 23, 31, 40, 16, 54, 42, 56, 29, 34, 13}),
    BOOK_05_DEU("Deuteronômio",       "Deut.",   "De" , new Integer[] {46, 37, 29, 49,33, 25, 26, 20, 29, 22,32, 32, 18, 29, 23, 22,20, 22, 21, 20, 23, 30,25, 22, 19, 19, 26, 68,29, 20, 30, 52, 29,12}),
    BOOK_06_JOS("Josué",              "Jos.",    "Jos", new Integer[] {18, 24,17, 24,15, 27,26, 35,27, 43,23, 24,33, 15,63, 10,18, 28,51, 9,45, 34,16,33}),
    BOOK_07_JUD("Juízes",             "Juí.",    "Jz" , new Integer[] {36,23,31,24,31,40,25,35,57,18,40,15,25,20,20,31,13,31,30,48,25}),
    BOOK_08_RUT("Rute",               "Rute",    "Ru" , new Integer[] {22,23,18,22}),
    BOOK_09_1SA("1 Samuel",           "1 Sam.",  "1Sa", new Integer[] {28,36,21,22,12,21,17,22,27,27,15,25,23,52,35,23,58,30,24,42,15,23,29,22,44,25,12,25,11,31,13}),
    BOOK_10_2SA("2 Samuel",           "2 Sam.",  "2Sa", new Integer[] {27,32,39,12,25,23,29,18,13,19,27,31,39,33,37,23,29,33,43,26,22,51,39,25}),
    BOOK_11_1KI("1 Reis",             "1 Reis",  "1Rs", new Integer[] {53,46,28,34,18,38,51,66,28,29,43,33,34,31,34,34,24,46,21,43,29,53}),
    BOOK_12_2KI("2 Reis",             "2 Reis",  "2Rs", new Integer[] {18,25,27,44,27,33,20,29,37,36,21,21,25,29,38,20,41,37,37,21,26,20,37,20,30}),
    BOOK_13_1CH("1 Crônicas",         "1 Crô.",  "1Cr", new Integer[] {54,55,24,43,26,81,40,40,44,14,47,40,14,17,29,43,27,17,19,8,30,19,32,31,31,32,34,21,30}),
    BOOK_14_2CH("2 Crônicas",         "2 Crô.",  "2Cr", new Integer[] {17,18,17,22,14,42,22,18,31,19,23,16,22,15,19,14,19,34,11,37,20,12,21,27,28,23,9,27,36,27,21,33,25,33,27,23}),
    BOOK_15_EZR("Esdras",             "Esd.",    "Esd", new Integer[] {11,70,13,24,17,22,28,36,15,44}),
    BOOK_16_NEH("Neemias",            "Nee.",    "Ne" , new Integer[] {11,20,32,23,19,19,73,18,38,39,36,47,31}),
    BOOK_17_EST("Ester",              "Ester",   "Est", new Integer[] {22,23,15,17,14,14,10,17,32,3}),
    BOOK_18_JOB("Jó",                 "Jó",      "Jó" , new Integer[] {22,13,26,21,27,30,21,22,35,22,20,25,28,22,35,22,16,21,29,29,34,30,17,25,6,14,23,28,25,31,40,22,33,37,16,33,24,41,30,24,34,17}),
    BOOK_19_PSA("Salmos",             "Sal.",    "Sal", new Integer[] {6,12,8,8,12,10,17,9,20,18,7,8,6,7,5,11,15,50,14,9,13,31,6,10,22,12,14,9,11,12,24,11,22,22,28,12,40,22,13,17,13,11,5,26,17,11,9,14,20,23,19,9,6,7,23,13,11,11,17,12,8,12,11,10,13,20,7,35,36,5,24,20,28,23,10,12,20,72,13,19,16,8,18,12,13,17,7,18,52,17,16,15,5,23,11,13,12,9,9,5,8,28,22,35,45,48,43,13,31,7,10,10,9,8,18,19,2,29,176,7,8,9,4,8,5,6,5,6,8,8,3,18,3,3,21,26,9,8,24,13,10,7,12,15,21,10,20,14,9,6}),
    BOOK_20_PRO("Provérbios",         "Pro.",    "Pr" , new Integer[] {33,22,35,27,23,35,27,36,18,32,31,28,25,35,33,33,28,24,29,30,31,29,35,34,28,28,27,28,27,33,31}),
    BOOK_21_ECC("Eclesiastes",        "Ecl.",    "Ec" , new Integer[] {18,26,22,16,20,12,29,17,18,20,10,14}),
    BOOK_22_SON("Cântico de Salomão", "Cân.",    "Cân", new Integer[] {17,17,11,16,16,13,13,14}),
    BOOK_23_ISA("Isaías",             "Isa.",    "Is" , new Integer[] {31,22,26,6,30,13,25,22,21,34,16,6,22,32,9,14,14,7,25,6,17,25,18,23,12,21,13,29,24,33,9,20,24,17,10,22,38,22,8,31,29,25,28,28,25,13,15,22,26,11,23,15,12,17,13,12,21,14,21,22,11,12,19,12,25,24}),
    BOOK_24_JER("Jeremias",           "Jer.",    "Je" , new Integer[] {19,37,25,31,31,30,34,22,26,25,23,17,27,22,21,21,27,23,15,18,14,30,40,10,38,24,22,17,32,24,40,44,26,22,19,32,21,28,18,16,18,22,13,30,5,28,7,47,39,46,64,34}),
    BOOK_25_LAM("Lamentações",        "Lam.",    "La" , new Integer[] {22,22,66,22,22}),
    BOOK_26_EZE("Ezequiel",           "Eze.",    "Ez" , new Integer[] {28,10,27,17,17,14,27,18,11,22,25,28,23,23,8,63,24,32,14,49,32,31,49,27,17,21,36,26,21,26,18,32,33,31,15,38,28,23,29,49,26,20,27,31,25,24,23,35}),
    BOOK_27_DAN("Daniel",             "Dan.",    "Da" , new Integer[] {21,49,30,37,31,28,28,27,27,21,45,13}),
    BOOK_28_HOS("Oseias",             "Ose.",    "Os" , new Integer[] {11,23,5,19,15,11,16,14,17,15,12,14,16,9}),
    BOOK_29_JOE("Joel",               "Joel",    "Jl" , new Integer[] {20,32,21}),
    BOOK_30_AMO("Amós",               "Amós",    "Am" , new Integer[] {15,16,15,13,27,14,17,14,15}),
    BOOK_31_OBA("Obadias",            "Obd.",    "Ob" , new Integer[] {21}),
    BOOK_32_JON("Jonas",              "Jonas",   "Jon", new Integer[] {17,10,10,11}),
    BOOK_33_MIC("Miqueias",           "Miq.",    "Miq", new Integer[] {16,13,12,13,15,16,20}),
    BOOK_34_NAH("Naum",               "Naum",    "Na" , new Integer[] {15,13,19}),
    BOOK_35_HAB("Habacuque",          "Hab.",    "Hab", new Integer[] {17,20,19}),
    BOOK_36_ZEP("Sofonias",           "Sof.",    "Sof", new Integer[] {18,15,20}),
    BOOK_37_HAG("Ageu",               "Ageu",    "Ag" , new Integer[] {15,23}),
    BOOK_38_ZEC("Zacarias",           "Zac.",    "Za" , new Integer[] {21,13,10,14,11,15,14,23,17,12,17,14,9,21}),
    BOOK_39_MAL("Malaquias",          "Mal.",    "Mal", new Integer[] {14,17,18,6}),
    BOOK_40_MAT("Mateus",             "Mat.",    "Mt" , new Integer[] {25,23,17,25,48,34,29,34,38,42,30,50,58,36,39,28,27,35,30,34,46,46,39,51,46,75,66,20}),
    BOOK_41_MAR("Marcos",             "Mar.",    "Mr" , new Integer[] {45,28,35,41,43,56,37,38,50,52,33,44,37,72,47,20}),
    BOOK_42_LUK("Lucas",              "Luc.",    "Lu" , new Integer[] {80,52,38,44,39,49,50,56,62,42,54,59,35,35,32,31,37,43,48,47,38,71,56,53}),
    BOOK_43_JOH("João",               "João",    "Jo" , new Integer[] {51,25,36,54,47,71,53,59,41,42,57,50,38,31,27,33,26,40,42,31,25}),
    BOOK_44_ACT("Atos",               "Atos",    "At" , new Integer[] {26,47,26,37,42,15,60,40,43,48,30,25,52,28,41,40,34,28,41,38,40,30,35,27,27,32,44,31}),
    BOOK_45_ROM("Romanos",            "Rom.",    "Ro" , new Integer[] {32,29,31,25,21,23,25,39,33,21,36,21,14,23,33,27}),
    BOOK_46_1CO("1 Coríntios",        "1 Cor.",  "1Co", new Integer[] {31,16,23,21,13,20,40,13,27,33,34,31,13,40,58,24}),
    BOOK_47_2CO("2 Coríntios",        "2 Cor.",  "2Co", new Integer[] {24,17,18,18,21,18,16,24,15,18,33,21,14}),
    BOOK_48_GAL("Gálatas",            "Gál.",    "Gál", new Integer[] {24,21,29,31,26,18}),
    BOOK_49_EPH("Efésios",            "Efé.",    "Ef" , new Integer[] {23,22,21,32,33,24}),
    BOOK_50_PHI("Filipenses",         "Fil.",    "Fil", new Integer[] {30,30,21,23}),
    BOOK_51_COL("Colossenses",        "Col.",    "Col", new Integer[] {29,23,25,18}),
    BOOK_52_1TH("1 Tessalonicenses",  "1 Tes.",  "1Te", new Integer[] {10,20,13,18,28}),
    BOOK_53_2TH("2 Tessalonicenses",  "2 Tes.",  "2Te", new Integer[] {12,17,18}),
    BOOK_54_1TI("1 Timóteo",          "1 Tim.",  "1Ti", new Integer[] {20,15,16,16,25,21}),
    BOOK_55_2TI("2 Timóteo",          "2 Tim.",  "2Ti", new Integer[] {18,26,17,22}),
    BOOK_56_TIT("Tito",               "Tito",    "Tit", new Integer[] {16,15,15}),
    BOOK_57_PHM("Filêmon",            "Filêm.",  "Flm", new Integer[] {25}),
    BOOK_58_HEB("Hebreus",            "Heb.",    "He" , new Integer[] {14,18,19,16,14,20,28,13,28,39,40,29,25}),
    BOOK_59_JAM("Tiago",              "Tia.",    "Tg" , new Integer[] {27,26,18,17,20}),
    BOOK_60_1PE("1 Pedro",            "1 Ped.",  "1Pe", new Integer[] {25,25,22,19,14}),
    BOOK_61_2PE("2 Pedro",            "2 Ped.",  "2Pe", new Integer[] {21,22,18}),
    BOOK_62_1JO("1 João",             "1 João",  "1Jo", new Integer[] {10,29,24,21,21}),
    BOOK_63_2JO("2 João",             "2 João",  "2Jo", new Integer[] {13}),
    BOOK_64_3JO("3 João",             "3 João",  "3Jo", new Integer[] {14}),
    BOOK_65_JUD("Judas",              "Judas",   "Ju" , new Integer[] {25}),
    BOOK_66_REV("Apocalipse",         "Apo.",    "Ap" , new Integer[] {20,29,22,11,14,17,17,13,21,11,19,17,18,20,8,21,18,24,21,15,27,21});

    private final String fullName;
    private final String abbreviation1;
    private final String abbreviation2;
    private final Integer[] scriptures;

    Book(String fullName, String abbreviation1, String abbreviation2, Integer[] scriptures) {
        this.fullName = fullName;
        this.abbreviation1 = abbreviation1;
        this.abbreviation2 = abbreviation2;
        this.scriptures = scriptures;
    }

    public String getFullName() {
        return this.fullName;
    }

    public String getAbbreviation1() {
        return this.abbreviation1;
    }

    public String getAbbreviation2() {
        return this.abbreviation2;
    }

    public String getMepsFormat() {
        return this.toString().substring(5);
    }

    public int getNumberOfChapters() {
        return scriptures.length;
    }

    public int getNumberOfScriptures(int chapter) {
        return scriptures[chapter - 1];
    }

    public static int getOrdinalValue(String book) {
        return Book.valueOf(book.toUpperCase()).ordinal() + 1;
    }

    public int getOrdinalValue() {
        return this.ordinal() + 1;
    }

    public static Book getBookNameFromFullName(String fullName) {
        for (Book book : Book.values()) {
            if (book.fullName.equals(fullName.trim())) {
                return book;
            }
        }
        return null;
    }

    public static Book getBookNameFromAbbreviation1(String abbreviation1) {
        for (Book book : Book.values()) {
            if (book.abbreviation1.equals(abbreviation1.trim())) {
                return book;
            }
        }
        return null;
    }

    public static Book getBookNameFromAbbreviation2(String abbreviation2) {
        for (Book book : Book.values()) {
            if (book.abbreviation2.equals(abbreviation2.trim())) {
                return book;
            }
        }
        return null;
    }

    public static ScriptureEarthBookName getScriptureEarthBookName(Book book) {
        return ScriptureEarthBookName.fromMepsFormatEnum(book);
    }

    public static Book getBookNameFromScriptureEarth(ScriptureEarthBookName scriptureEarthBook) {
        for (ScriptureEarthBookName book : ScriptureEarthBookName.values()) {
            if (book.equals(scriptureEarthBook)) {
                return book.getBook();
            }
        }
        return null;
    }
}
