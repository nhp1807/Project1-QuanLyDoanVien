package com.example.QuanLyDoanVien.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.QuanLyDoanVien.entity.DanhGia;

@Repository
public interface DanhGiaRepository extends JpaRepository<DanhGia, Long> {
    @Query("SELECT dg FROM DanhGia dg WHERE dg.maDoanVien = ?1")
    List<DanhGia> getListDanhGias(String maDoanVien);
}
