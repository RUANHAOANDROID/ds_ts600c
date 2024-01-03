package com.reader.api;

public class ID2Txt {
    public String mName;
    public String mGender;
    public String mGenderIndex;
    public String mNational;
    public String mNationalIndex;
    public String mBirthYear;
    public String mBirthMonth;
    public String mBirthDay;
    public String mBirthDate;
    public String mAddress;
    public String mID2Num;
    public String mIssue;
    public String mBegin;
    public String mEnd;

    public ID2Txt(byte[] data) {
        decode(data);
    }

    public ID2Txt() {
    }

    public void decode(byte[] _txt) {
        try {

            this.mName = new String(_txt, 0, 30, "UTF-16LE");

            this.mGenderIndex = new String(_txt, 30, 2, "UTF-16LE");

            this.mNationalIndex = new String(_txt, 32, 4, "UTF-16LE");

            this.mBirthYear = new String(_txt, 36, 8, "UTF-16LE");

            this.mBirthMonth = new String(_txt, 44, 4, "UTF-16LE");

            this.mBirthDay = new String(_txt, 48, 4, "UTF-16LE");

            this.mBirthDate = mBirthYear + mBirthMonth + mBirthDay;

            this.mAddress = new String(_txt, 52, 70, "UTF-16LE");

            this.mID2Num = new String(_txt, 122, 36, "UTF-16LE");

            this.mIssue = new String(_txt, 158, 30, "UTF-16LE");

            this.mBegin = new String(_txt, 188, 16, "UTF-16LE");

            this.mEnd = new String(_txt, 204, 16, "UTF-16LE");

            this.mGender = GetGenderFromCode(this.mGenderIndex);

            this.mNational = GetNationalFromCode(this.mNationalIndex);

        } catch (Exception e) {

        }
    }

    private String GetGenderFromCode(String genderCode) {
        switch (Integer.parseInt(genderCode)) {
            case 0:
                return "未知的性别";
            case 1:
                return "男";
            case 2:
                return "女";
            case 9:
                return "未说明的性别";
        }
        return "未定义的性别";
    }

    private String GetNationalFromCode(String nationalCode) {
        int n = Integer.valueOf(nationalCode);
        switch (n) {
            case 1:
                return "汉";
            case 2:
                return "蒙古";
            case 3:
                return "回";
            case 4:
                return "藏";
            case 5:
                return "维吾尔";
            case 6:
                return "苗";
            case 7:
                return "彝";
            case 8:
                return "壮";
            case 9:
                return "布依";
            case 10:
                return "朝鲜";
            case 11:
                return "满";
            case 12:
                return "侗";
            case 13:
                return "瑶";
            case 14:
                return "白";
            case 15:
                return "土家";
            case 16:
                return "哈尼";
            case 17:
                return "哈萨克";
            case 18:
                return "傣";
            case 19:
                return "黎";
            case 20:
                return "傈僳";
            case 21:
                return "佤";
            case 22:
                return "畲";
            case 23:
                return "高山";
            case 24:
                return "拉祜";
            case 25:
                return "水";
            case 26:
                return "东乡";
            case 27:
                return "纳西";
            case 28:
                return "景颇";
            case 29:
                return "柯尔克孜";
            case 30:
                return "土";
            case 31:
                return "达斡尔";
            case 32:
                return "仫佬";
            case 33:
                return "羌";
            case 34:
                return "布朗";
            case 35:
                return "撒拉";
            case 36:
                return "毛难";
            case 37:
                return "仡佬";
            case 38:
                return "锡伯";
            case 39:
                return "阿昌";
            case 40:
                return "普米";
            case 41:
                return "塔吉克";
            case 42:
                return "怒";
            case 43:
                return "乌孜别克";
            case 44:
                return "俄罗斯";
            case 45:
                return "鄂温克";
            case 46:
                return "崩龙";
            case 47:
                return "保安";
            case 48:
                return "裕固";
            case 49:
                return "京";
            case 50:
                return "塔塔尔";
            case 51:
                return "独龙";
            case 52:
                return "鄂伦春";
            case 53:
                return "赫哲";
            case 54:
                return "门巴";
            case 55:
                return "珞巴";
            case 56:
                return "基诺";
        }
        return "其他";
    }

    @Override
    public String toString() {
        return "ID2Txt{" +
                "mName='" + mName + '\'' +
                ", mGender='" + mGender + '\'' +
                ", mGenderIndex='" + mGenderIndex + '\'' +
                ", mNational='" + mNational + '\'' +
                ", mNationalIndex='" + mNationalIndex + '\'' +
                ", mBirthYear='" + mBirthYear + '\'' +
                ", mBirthMonth='" + mBirthMonth + '\'' +
                ", mBirthDay='" + mBirthDay + '\'' +
                ", mAddress='" + mAddress + '\'' +
                ", mID2Num='" + mID2Num + '\'' +
                ", mIssue='" + mIssue + '\'' +
                ", mBegin='" + mBegin + '\'' +
                ", mEnd='" + mEnd + '\'' +
                '}';
    }
}
