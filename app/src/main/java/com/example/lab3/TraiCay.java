package com.example.lab3;

import android.net.Uri;

public class TraiCay {
    private String ten;
    private String mota;
    private Uri hinh;

    public TraiCay(String ten, String mota, Uri hinh) {
        this.ten = ten;
        this.mota = mota;
        this.hinh = hinh;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public Uri getHinh() {
        return hinh;
    }

    public void setHinh(Uri hinh) {
        this.hinh = hinh;
    }

}
