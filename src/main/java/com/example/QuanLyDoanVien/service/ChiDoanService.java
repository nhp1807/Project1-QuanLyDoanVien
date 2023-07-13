package com.example.QuanLyDoanVien.service;

import java.util.List;

import com.example.QuanLyDoanVien.entity.ChiDoan;

public interface ChiDoanService {
    List<ChiDoan> getAllChiDoan();
    ChiDoan saveChiDoan(ChiDoan chiDoan);
    ChiDoan getChiDoanById(Long id);
    ChiDoan updateChiDoan(ChiDoan chiDoan);
    void deleteChiDoan(Long id);
    Boolean ifChiDoanExisted(String tenChiDoan);
}
