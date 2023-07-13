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
@Table(name = "chidoans")
public class ChiDoan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String tenChiDoan;


    public ChiDoan(String tenChiDoan) {
        this.tenChiDoan = tenChiDoan;
    }
}
