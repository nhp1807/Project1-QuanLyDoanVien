package com.example.QuanLyDoanVien.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.QuanLyDoanVien.entity.DoanVien;
import com.example.QuanLyDoanVien.repository.DoanVienRepository;

import java.util.List;

@Service
public class DoanVienServiceImpl implements DoanVienService{
    @Autowired
    DoanVienRepository doanVienRepository;

    @Override
    public List<DoanVien> getAllDoanVien() {
        return doanVienRepository.findAll();
    }

    @Override
    public DoanVien saveDoanVien(DoanVien doanVien) {
        return doanVienRepository.save(doanVien);
    }

    @Override
    public DoanVien getDoanVienById(Long id) {
        return doanVienRepository.findById(id).get();
    }

    @Override
    public DoanVien updateDoanVien(DoanVien doanVien) {
        return doanVienRepository.save(doanVien);
    }

    @Override
    public void deleteDoanVien(Long id) {
        doanVienRepository.deleteById(id);
    }

    @Override
    public Boolean ifDoanVienExistedEmail(String email) {
        DoanVien doanVien = doanVienRepository.findByEmail(email);
        if(doanVien != null){
            return false;
        }
        return true;
    }

    @Override
    public Boolean ifDoanVienExistedMaDV(String maDoanVien) {
        DoanVien doanVien = doanVienRepository.findByMaDoanVien(maDoanVien);
        if(doanVien != null){
            return false;
        }
        return true;
    }
}
