package com.example.QuanLyDoanVien.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "doanviens")
public class DoanVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long idChiDoan;
    @Column
    private String tenChiDoan;
    @Column(unique = true)
    private String maDoanVien;
    @Column
    private String hoTen;
    @Column
    private String cccd;
    @Column
    private String ngaySinh;
    @Column
    private String queQuan;
    @Column
    private String danToc;
    @Column
    private String sdt;
    @Column
    private String chucVu;
    @Column
    private String ngayVaoDoan;
    @Column(unique = true)
    private String email;
    @Column
    private String matKhau;
    @Column
    private String reMatKhau;
    @Enumerated(EnumType.STRING)
    private Role role;
    public DoanVien(Long idChiDoan, String tenChiDoan, String maDoanVien, String hoTen, String cccd, String ngaySinh, String queQuan,
            String danToc, String sdt, String chucVu, String ngayVaoDoan, String email, String matKhau, String reMatKhau, Role role) {
        this.idChiDoan = idChiDoan;
        this.tenChiDoan = tenChiDoan;
        this.maDoanVien = maDoanVien;
        this.hoTen = hoTen;
        this.cccd = cccd;
        this.ngaySinh = ngaySinh;
        this.queQuan = queQuan;
        this.danToc = danToc;
        this.sdt = sdt;
        this.chucVu = chucVu;
        this.ngayVaoDoan = ngayVaoDoan;
        this.email = email;
        this.matKhau = matKhau;
        this.reMatKhau = reMatKhau;
        this.role = role;
    }

    
}
