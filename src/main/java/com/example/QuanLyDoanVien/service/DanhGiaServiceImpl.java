package com.example.QuanLyDoanVien.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.QuanLyDoanVien.entity.DanhGia;
import com.example.QuanLyDoanVien.repository.DanhGiaRepository;

import java.util.List;

@Service
public class DanhGiaServiceImpl implements DanhGiaService{
    @Autowired
    DanhGiaRepository danhGiaRepository;

    @Override
    public List<DanhGia> getAllDanhGia() {
        return danhGiaRepository.findAll();
    }

    @Override
    public DanhGia saveDanhGia(DanhGia danhGia) {
        return danhGiaRepository.save(danhGia);
    }

    @Override
    public DanhGia getDanhGiaById(Long id) {
        return danhGiaRepository.findById(id).get();
    }

    @Override
    public DanhGia updateDanhGia(DanhGia danhGia) {
        return danhGiaRepository.save(danhGia);
    }

    @Override
    public void deleteDanhGia(Long id) {
        danhGiaRepository.deleteById(id);
    }
}
