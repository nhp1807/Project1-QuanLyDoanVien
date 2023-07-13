package com.example.QuanLyDoanVien.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.QuanLyDoanVien.entity.ChiDoan;
import com.example.QuanLyDoanVien.repository.ChiDoanRepository;

import java.util.List;

@Service
public class ChiDoanServiceImpl implements ChiDoanService{
    @Autowired
    ChiDoanRepository chiDoanRepository;
    @Override
    public List<ChiDoan> getAllChiDoan() {
        return chiDoanRepository.findAll();
    }

    @Override
    public ChiDoan saveChiDoan(ChiDoan chiDoan) {
        return chiDoanRepository.save(chiDoan);
    }

    @Override
    public ChiDoan getChiDoanById(Long id) {
        return chiDoanRepository.findById(id).get();
    }

    @Override
    public ChiDoan updateChiDoan(ChiDoan chiDoan) {
        return chiDoanRepository.save(chiDoan);
    }

    @Override
    public void deleteChiDoan(Long id) {
        chiDoanRepository.deleteById(id);
    }

    @Override
    public Boolean ifChiDoanExisted(String tenChiDoan) {
        ChiDoan chiDoan = chiDoanRepository.findByTenChiDoan(tenChiDoan);
        if(chiDoan != null){
            return false;
        }
        return true;
    }
}
