package com.company.makepub.app.domain;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ScriptureEarthBookName {

    BOOK_GEN(Book.BOOK_01_GEN),
    BOOK_EXO(Book.BOOK_02_EXO),
    BOOK_LEV(Book.BOOK_03_LEV),
    BOOK_NUM(Book.BOOK_04_NUM),
    BOOK_DEU(Book.BOOK_05_DEU),
    BOOK_JOS(Book.BOOK_06_JOS),
    BOOK_JDG(Book.BOOK_07_JUD),
    BOOK_RUT(Book.BOOK_08_RUT),
    BOOK_1SA(Book.BOOK_09_1SA),
    BOOK_2SA(Book.BOOK_10_2SA),
    BOOK_1KI(Book.BOOK_11_1KI),
    BOOK_2KI(Book.BOOK_12_2KI),
    BOOK_1CH(Book.BOOK_13_1CH),
    BOOK_2CH(Book.BOOK_14_2CH),
    BOOK_EZR(Book.BOOK_15_EZR),
    BOOK_NEH(Book.BOOK_16_NEH),
    BOOK_EST(Book.BOOK_17_EST),
    BOOK_JOB(Book.BOOK_18_JOB),
    BOOK_PSA(Book.BOOK_19_PSA),
    BOOK_PRO(Book.BOOK_20_PRO),
    BOOK_ECC(Book.BOOK_21_ECC),
    BOOK_SNG(Book.BOOK_22_SON),
    BOOK_ISA(Book.BOOK_23_ISA),
    BOOK_JER(Book.BOOK_24_JER),
    BOOK_LAM(Book.BOOK_25_LAM),
    BOOK_EZK(Book.BOOK_26_EZE),
    BOOK_DAN(Book.BOOK_27_DAN),
    BOOK_HOS(Book.BOOK_28_HOS),
    BOOK_JOL(Book.BOOK_29_JOE),
    BOOK_AMO(Book.BOOK_30_AMO),
    BOOK_OBA(Book.BOOK_31_OBA),
    BOOK_JON(Book.BOOK_32_JON),
    BOOK_MIC(Book.BOOK_33_MIC),
    BOOK_NAM(Book.BOOK_34_NAH),
    BOOK_HAB(Book.BOOK_35_HAB),
    BOOK_ZEP(Book.BOOK_36_ZEP),
    BOOK_HAG(Book.BOOK_37_HAG),
    BOOK_ZEC(Book.BOOK_38_ZEC),
    BOOK_MAL(Book.BOOK_39_MAL),
    BOOK_MAT(Book.BOOK_40_MAT),
    BOOK_MRK(Book.BOOK_41_MAR),
    BOOK_LUK(Book.BOOK_42_LUK),
    BOOK_JHN(Book.BOOK_43_JOH),
    BOOK_ACT(Book.BOOK_44_ACT),
    BOOK_ROM(Book.BOOK_45_ROM),
    BOOK_1CO(Book.BOOK_46_1CO),
    BOOK_2CO(Book.BOOK_47_2CO),
    BOOK_GAL(Book.BOOK_48_GAL),
    BOOK_EPH(Book.BOOK_49_EPH),
    BOOK_PHP(Book.BOOK_50_PHI),
    BOOK_COL(Book.BOOK_51_COL),
    BOOK_1TH(Book.BOOK_52_1TH),
    BOOK_2TH(Book.BOOK_53_2TH),
    BOOK_1TI(Book.BOOK_54_1TI),
    BOOK_2TI(Book.BOOK_55_2TI),
    BOOK_TIT(Book.BOOK_56_TIT),
    BOOK_PHM(Book.BOOK_57_PHM),
    BOOK_HEB(Book.BOOK_58_HEB),
    BOOK_JAS(Book.BOOK_59_JAM),
    BOOK_1PE(Book.BOOK_60_1PE),
    BOOK_2PE(Book.BOOK_61_2PE),
    BOOK_1JN(Book.BOOK_62_1JO),
    BOOK_2JN(Book.BOOK_63_2JO),
    BOOK_3JN(Book.BOOK_64_3JO),
    BOOK_JUD(Book.BOOK_65_JUD),
    BOOK_REV(Book.BOOK_66_REV);

    private final Book book;

    @Nonnull
    public String getName() {
        String bookString = this.toString();
        if(bookString==null || bookString.isBlank() || bookString.length()<5){
            return "";
        }
        return this.toString().substring(5);
    }

    @Nullable
    public static ScriptureEarthBookName fromScriptureEarthString(@Nonnull String bookName) {
        for (ScriptureEarthBookName name : ScriptureEarthBookName.values()) {
            String enumName = name.getName();
            if (enumName.equals(bookName)) {
                return name;
            }
        }
        return null;
    }

    @Nullable
    public static ScriptureEarthBookName fromMepsFormatEnum(@Nonnull Book book) {
        for (ScriptureEarthBookName scriptureEarthBookName : ScriptureEarthBookName.values()) {
            if (scriptureEarthBookName.book.equals(book)) {
                return scriptureEarthBookName;
            }
        }
        return null;
    }

    public static Book getMepsFormatFromScriptureEarthEnum(@Nonnull ScriptureEarthBookName scriptureEarthBookName) {
        return scriptureEarthBookName.book;
    }

}
