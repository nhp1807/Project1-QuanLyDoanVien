package com.example.QuanLyDoanVien.service;

import java.util.List;

import com.example.QuanLyDoanVien.entity.DanhGia;

public interface DanhGiaService {
    List<DanhGia> getAllDanhGia();
    DanhGia saveDanhGia(DanhGia danhGia);
    DanhGia getDanhGiaById(Long id);
    DanhGia updateDanhGia(DanhGia danhGia);
    void deleteDanhGia(Long id);
}
