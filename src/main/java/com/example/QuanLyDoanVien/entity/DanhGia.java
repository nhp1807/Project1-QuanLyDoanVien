package com.example.QuanLyDoanVien.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "danhgias")
public class DanhGia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String maDoanVien;
    @Column
    private String hoTen;
    @Column
    private String nguoiDanhGia;
    @Column
    private String namHoc;
    @Column
    private String noiDung;

    public DanhGia(String maDoanVien, String nguoiDanhGia, String namHoc, String noiDung) {
        this.maDoanVien = maDoanVien;
        this.nguoiDanhGia = nguoiDanhGia;
        this.namHoc = namHoc;
        this.noiDung = noiDung;
    }
}
