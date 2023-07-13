package com.example.QuanLyDoanVien.service;

import java.util.List;

import com.example.QuanLyDoanVien.entity.DoanVien;

public interface DoanVienService {
    List<DoanVien> getAllDoanVien();
    DoanVien saveDoanVien(DoanVien doanVien);
    DoanVien getDoanVienById(Long id);
    DoanVien updateDoanVien(DoanVien doanVien);
    void deleteDoanVien(Long id);
    Boolean ifDoanVienExistedEmail(String email);
    Boolean ifDoanVienExistedMaDV(String maDoanVien);
}
