package com.example.QuanLyDoanVien.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.QuanLyDoanVien.entity.ChiDoan;
import com.example.QuanLyDoanVien.entity.DanhGia;
import com.example.QuanLyDoanVien.entity.DoanVien;
import com.example.QuanLyDoanVien.entity.Role;
import com.example.QuanLyDoanVien.entity.User;
import com.example.QuanLyDoanVien.repository.ChiDoanRepository;
import com.example.QuanLyDoanVien.repository.DanhGiaRepository;
import com.example.QuanLyDoanVien.repository.DoanVienRepository;
import com.example.QuanLyDoanVien.repository.UserRepository;
import com.example.QuanLyDoanVien.service.ChiDoanService;
import com.example.QuanLyDoanVien.service.DanhGiaService;
import com.example.QuanLyDoanVien.service.DoanVienService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AppController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChiDoanService chiDoanService;
    @Autowired
    private ChiDoanRepository chiDoanRepository;
    @Autowired
    private DoanVienService doanVienService;
    @Autowired
    private DoanVienRepository doanVienRepository;
    @Autowired
    private DanhGiaService danhGiaService;
    @Autowired
    private DanhGiaRepository danhGiaRepository;
    
    public String tenChiDoan;
    public Long idChiDoan;
    public Long idDoanVienDuocDanhGia;
    public Long idCanBo;
    public static Long idDangNhap;
    public int count = 0;
    public int countSaiPass1 = 0;    
    public int countSaiPass2 = 0;
    public int errMaDoanVien = 0;
    public int errTenChiDoan = 0;
    public int errEmail = 0;
    public Long idLop;

    /*
     * Hiện thị giao diện login và register
     */
    // @GetMapping("/")
    // public String viewHomePage() {
    //     return "home";
    // }

    @GetMapping("/errorLogin")
    public String showLoginErrorPage(){
        return "errorLogin";
    }

    @GetMapping("/login")
    public String showLoginPage(Model model){
        // Check if an error occurred during login
        boolean isError = (boolean) model.getAttribute("error");
        if (isError) {
            // Get the error message from the session and pass it to the login page
            String errorMessage = (String) model.getAttribute("errorMessage");
            model.addAttribute("errorMessage", errorMessage);
        }
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());

        return "signup_form";
    }

    @PostMapping("/process_register")
    public String processRegister(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRole(Role.ADMIN);

        userRepository.save(user);

        return "register_success";
    }

    @GetMapping("/admin/login")
    public String showAdminLoginPage() {
        return "ad-login";
    }

    @GetMapping("/admin/logout")
    public String logoutAdmin() {
        return "redirect:/login";
    }

    @GetMapping("/can-bo/login")
    public String showCanboLoginPage() {
        return "cb-login";
    }

    @GetMapping("/can-bo/logout")
    public String logoutCanbo() {
        return "redirect:/login";
    }

    @GetMapping("/doan-vien/login")
    public String showDoanvienLoginPage() {
        return "dv-login";
    }

    @GetMapping("/doan-vien/logout")
    public String logoutDoanvien() {
        return "redirect:/login";
    }

    /*
     * Hiển thị list người dùng admin
     */
    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> listUsers = userRepository.findAll();
        model.addAttribute("listUsers", listUsers);

        return "users";
    }

    // ----------------------------------------ADMIN------------------------------------------
    /*
     * Hiển thị danh sách các chi đoàn
     */
    @GetMapping("/admin/danh-sach-chi-doan")
    public String listChiDoans(Model model, @Param("keyword") String keyword) {
        if (keyword != null) {
            List<ChiDoan> listChiDoans = chiDoanRepository.findByTenChiDoanContaining(keyword);
            model.addAttribute("listChiDoans", listChiDoans);
        } else {
            List<ChiDoan> listChiDoans = chiDoanService.getAllChiDoan();
            model.addAttribute("listChiDoans", listChiDoans);
        }

        return "ad-danh-sach-chi-doan";
    }

    /*
     * Hiển thị giao diện thêm chi đoàn cho admin
     */
    @GetMapping("/admin/danh-sach-chi-doan/them-chi-doan")
    public String createChiDoanForm(Model model) {
        ChiDoan chiDoan = new ChiDoan();
        
        model.addAttribute("chidoan", chiDoan);
        if(count != 0){
            model.addAttribute("errTenChiDoan", "Tên chi đoàn đã tồn tại");
            count = 0;
        }
        
        return "ad-them-chi-doan";
    }

    /*
     * Lưu chi đoàn vào cơ sở dữ liệu
     */
    @PostMapping("/admin/danh-sach-chi-doan-cd")
    public String saveChiDoan(@Valid @ModelAttribute("chidoan") ChiDoan chiDoan, BindingResult bindingResult, Model model) {
        if(!chiDoanService.ifChiDoanExisted(chiDoan.getTenChiDoan())){
            count++;
            return "redirect:/admin/danh-sach-chi-doan/them-chi-doan";
        }

        chiDoanService.saveChiDoan(chiDoan);

        return "redirect:/admin/danh-sach-chi-doan";
    }

    /*
     * Hiển thị giao diện chỉnh sửa chi đoàn cho admin
     */
    @GetMapping("/admin/danh-sach-chi-doan/chinh-sua/{id}")
    public String updateChiDoanForm(@PathVariable Long id, Model model) {
        if(count != 0){
            System.out.println("Trùng tên chi đoàn");
            model.addAttribute("errTenChiDoan", "Tên chi đoàn đã tồn tại");
            count = 0;
            
        }

        model.addAttribute("chidoan", chiDoanService.getChiDoanById(id));

        return "ad-chinh-sua-chi-doan";
    }

    /*
     * Lưu chi đoàn đã chỉnh sửa vào cơ sở dữ liệu
     */
    @PostMapping("/admin/danh-sach-chi-doan/{id}")
    public String updateChiDoan(@PathVariable Long id, @ModelAttribute("chidoan") ChiDoan chiDoan, Model model) {
        if(!chiDoanService.ifChiDoanExisted(chiDoan.getTenChiDoan())){
            count++;
            return "redirect:/admin/danh-sach-chi-doan/chinh-sua/{id}";
        }

        tenChiDoan = chiDoanService.getChiDoanById(id).getTenChiDoan();
        Long idChiDoan = chiDoanService.getChiDoanById(id).getId();
        List<DoanVien> doanViens = doanVienRepository.getListDoanVien(idChiDoan);
        for (DoanVien dv : doanViens) {
            dv.setTenChiDoan(chiDoan.getTenChiDoan());
        }

        ChiDoan existingChiDoan = chiDoanService.getChiDoanById(id);
        existingChiDoan.setId(id);
        existingChiDoan.setTenChiDoan(chiDoan.getTenChiDoan());

        chiDoanService.updateChiDoan(existingChiDoan);

        return "redirect:/admin/danh-sach-chi-doan";
    }

    /*
     * Xoá chi đoàn
     */
    @GetMapping("/admin/danh-sach-chi-doan/{id}")
    public String deleteChiDoan(@PathVariable Long id) {
        // tenChiDoan = chiDoanService.getChiDoanById(id).getTenChiDoan();
        List<DoanVien> doanViens = doanVienRepository.getListDoanVien(id);
        for (DoanVien dv : doanViens) {
            doanVienRepository.delete(dv);
            User user = userRepository.findByEmail(dv.getEmail());
            userRepository.delete(user);
        }

        chiDoanService.deleteChiDoan(id);

        return "redirect:/admin/danh-sach-chi-doan";
    }

    /*
     * Hiển thị giao diện thêm cán bộ cho chi đoàn cụ thể của admin
     */
    @GetMapping("/admin/danh-sach-chi-doan/them-can-bo/{id}")
    public String createCanBoForm(Model model, @PathVariable Long id) {
        DoanVien doanVien = new DoanVien();
        tenChiDoan = chiDoanService.getChiDoanById(id).getTenChiDoan();
        idLop = id;
        System.out.println(idLop);
        if(errMaDoanVien != 0){
            System.out.println("Trùng mã");
            model.addAttribute("errMaDoanVien", "Mã đoàn viên đã tồn tại");
            errMaDoanVien = 0;
            
        }

        if(errEmail != 0){
            System.out.println("Trùng email");
            model.addAttribute("errEmail", "Email đã tồn tại");
            errEmail = 0;
        }

        model.addAttribute("doanvien", doanVien);

        return "ad-them-can-bo";
    }

    /*
     * Lưu cán bộ đã được thêm vào cơ sở dữ liệu
     */
    @PostMapping("/admin/danh-sach-chi-doan-cb")
    public String saveCanBo(@ModelAttribute("doanvien") DoanVien doanVien) {
        if(!doanVienService.ifDoanVienExistedMaDV(doanVien.getMaDoanVien())){
            errMaDoanVien++;
            if(!doanVienService.ifDoanVienExistedEmail(doanVien.getEmail())){
                errEmail++;
                return "redirect:/admin/danh-sach-chi-doan/them-can-bo/" + idLop;
            }
            return "redirect:/admin/danh-sach-chi-doan/them-can-bo/" + idLop;
        }else{
            if(!doanVienService.ifDoanVienExistedEmail(doanVien.getEmail())){
                errEmail++;
                return "redirect:/admin/danh-sach-chi-doan/them-can-bo/" + idLop;
            }
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(doanVien.getMatKhau());
        doanVien.setMatKhau(encodedPassword);
        doanVien.setReMatKhau(encodedPassword);
        doanVien.setIdChiDoan(idLop);
        doanVien.setTenChiDoan(chiDoanService.getChiDoanById(idLop).getTenChiDoan());
        
        if(doanVien.getChucVu().equalsIgnoreCase("Đoàn viên")){
            doanVien.setRole(Role.DOANVIEN);
        }else{
            doanVien.setRole(Role.CANBO);
        }

        User user = new User();

        DoanVien dv = doanVienRepository.getDoanVienByChucVu(doanVien.getChucVu(), idLop);
        System.out.println(dv == null);
        if(dv != null){
            dv.setChucVu("Đoàn viên");
            dv.setRole(Role.DOANVIEN);
            User newUser = userRepository.findByEmail(dv.getEmail());
            newUser.setRole(Role.DOANVIEN);
        }

        String fullName = doanVien.getHoTen();
        String[] nameSplited = fullName.split(" ");
        String firstName = nameSplited[0];
        String lastName = fullName.substring(firstName.length()+1).trim();
        user.setFirstName(firstName);
        user.setLastName(lastName);

        user.setEmail(doanVien.getEmail());
        user.setPassword(doanVien.getMatKhau());
        user.setRole(doanVien.getRole());

        userRepository.save(user);
        doanVienService.saveDoanVien(doanVien);

        return "redirect:/admin/danh-sach-chi-doan";
    }

    /*
     * Hiển thị danh sách cán bộ của chi đoàn cụ thể
     */
    @GetMapping("/admin/danh-sach-chi-doan/danh-sach-can-bo/{id}")
    public String listCanBos(@PathVariable Long id, Model model) {
        // String tenChiDoan = chiDoanService.getChiDoanById(id).getTenChiDoan();
        List<DoanVien> listDoanViens = doanVienRepository.getListDoanVien(id);
        List<DoanVien> listCanBos = new ArrayList();
        for (DoanVien doanVien : listDoanViens) {
            if (doanVien.getRole().equals(Role.CANBO)) {
                listCanBos.add(doanVien);
            }
        }
        model.addAttribute("listCanBos", listCanBos);

        return "ad-danh-sach-can-bo";
    }

    // ---------------------------------------CÁN BỘ-----------------------------------------------

    /*
     * Hiển thị giao diện chỉnh sửa thông tin cá nhân
     */
    @GetMapping("/can-bo/thong-tin-doan-vien/chinh-sua/{id}")
    public String updateDoanVienForm(@PathVariable Long id, Model model) {
        model.addAttribute("doanvien", doanVienService.getDoanVienById(id));
        model.addAttribute("tenChiDoan", chiDoanService.getChiDoanById(doanVienService.getDoanVienById(id).getIdChiDoan()).getTenChiDoan());

        return "cb-chinh-sua-thong-tin";
    }

    /*
     * Hiển thị giao diện chỉnh sửa thông tin đoàn viên
     */
    @GetMapping("/can-bo/doan-vien/chinh-sua/{id}")
    public String updateDoanVienForm1(@PathVariable Long id, Model model) {
        model.addAttribute("doanvien", doanVienService.getDoanVienById(id));
        model.addAttribute("tenChiDoan", chiDoanService.getChiDoanById(doanVienService.getDoanVienById(id).getIdChiDoan()).getTenChiDoan());
        // System.out.println(idDangNhap);

        return "cb-chinh-sua-doan-vien";
    }

    /*
     * Cập nhật thông tin đoàn viên dựa vào id
     */
    @PostMapping("/can-bo/thong-tin-doan-vien/{id}")
    public String updateCanbo(@PathVariable Long id, @ModelAttribute("doanvien") DoanVien doanVien, Model model) {
        DoanVien existingDoanVien = doanVienService.getDoanVienById(id);
        Long currentClass = existingDoanVien.getIdChiDoan();
        User user = userRepository.findByEmail(existingDoanVien.getEmail());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        existingDoanVien.setIdChiDoan(chiDoanRepository.findByTenChiDoanContaining(doanVien.getTenChiDoan()).get(0).getId());
        // existingDoanVien.setIdChiDoan(existingDoanVien.getIdChiDoan());
        existingDoanVien.setTenChiDoan(doanVien.getTenChiDoan());
        existingDoanVien.setMaDoanVien(doanVien.getMaDoanVien());
        existingDoanVien.setHoTen(doanVien.getHoTen());
        existingDoanVien.setCccd(doanVien.getCccd());
        existingDoanVien.setQueQuan(doanVien.getQueQuan());
        existingDoanVien.setDanToc(doanVien.getDanToc());
        existingDoanVien.setNgaySinh(doanVien.getNgaySinh());
        existingDoanVien.setSdt(doanVien.getSdt());
        existingDoanVien.setNgayVaoDoan(doanVien.getNgayVaoDoan());
        // DoanVien dv = doanVienRepository.getDoanVienByChucVu(doanVien.getChucVu(), existingDoanVien.getIdChiDoan());
        // if (dv != null) {
        //     dv.setChucVu("Đoàn viên");
        //     dv.setRole(Role.DOANVIEN);
        //     User newUser = userRepository.findByEmail(dv.getEmail());
        //     newUser.setRole(Role.DOANVIEN);
        // }
        existingDoanVien.setChucVu(doanVien.getChucVu());
        if (doanVien.getChucVu().equalsIgnoreCase("Đoàn viên")) {
            existingDoanVien.setRole(Role.DOANVIEN);
            user.setRole(Role.DOANVIEN);
            userRepository.save(user);
            doanVienService.updateDoanVien(existingDoanVien);
            return "redirect:/can-bo/danh-sach-doan-vien";
        } else {
            existingDoanVien.setRole(Role.CANBO);
            user.setRole(Role.CANBO);

            if(id == idDangNhap){
                existingDoanVien.setEmail(doanVienService.getDoanVienById(idDangNhap).getEmail());
                doanVienService.updateDoanVien(existingDoanVien);
                return "redirect:/can-bo/danh-sach-doan-vien";
            }

            if(currentClass != existingDoanVien.getIdChiDoan()){
                DoanVien dv = doanVienRepository.getDoanVienByChucVu(doanVien.getChucVu(), existingDoanVien.getIdChiDoan());
                if (dv != null) {
                    dv.setChucVu("Đoàn viên");
                    dv.setRole(Role.DOANVIEN);
                    User newUser = userRepository.findByEmail(dv.getEmail());
                    newUser.setRole(Role.DOANVIEN);
                }
                
                userRepository.save(user);
                doanVienService.updateDoanVien(existingDoanVien);
                return "redirect:/can-bo/danh-sach-doan-vien";
            }

            DoanVien dv = doanVienRepository.getDoanVienByChucVu(doanVien.getChucVu(), existingDoanVien.getIdChiDoan());
            if (dv != null) {
                dv.setChucVu("Đoàn viên");
                dv.setRole(Role.DOANVIEN);
                User newUser = userRepository.findByEmail(dv.getEmail());
                newUser.setRole(Role.DOANVIEN);

                userRepository.save(user);
                doanVienService.updateDoanVien(existingDoanVien);

                if(dv.getId() == idDangNhap){
                    return "redirect:/login";
                }

                return "redirect:/can-bo/danh-sach-doan-vien";
            }
            
            userRepository.save(user);
            doanVienService.updateDoanVien(existingDoanVien);

            return "redirect:/can-bo/danh-sach-doan-vien";
        }
    }
    
    /**
     * Hiển thị giao diện đổi mật khẩu
     */
    @GetMapping("/can-bo/doi-mat-khau")
    public String doiMatKhauCanBoForm(Model model, Principal principal){
        String email = principal.getName();
        model.addAttribute("doanvien", doanVienRepository.findByEmail(email));
        model.addAttribute("tenChiDoan", doanVienRepository.findByEmail(email).getTenChiDoan());

        if(countSaiPass1 != 0){
            model.addAttribute("errSaiPass", "Mật khẩu chưa chính xác");
            countSaiPass1 = 0;
        }

        return "cb-doi-mat-khau";
    }

    /**
     * Thực hiện đổi mật khẩu
     */
    @PostMapping("/can-bo/doi-mat-khau")
    public String doiMatKhauCanBo(Model model, Principal principal, @ModelAttribute("doanvien") DoanVien doanVien){
        String email = principal.getName();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        DoanVien existingDoanVien = doanVienRepository.findByEmail(email);
        User user = userRepository.findByEmail(existingDoanVien.getEmail());

        if(!doanVien.getMatKhau().equals(doanVien.getReMatKhau())){
            countSaiPass1++;
            return "redirect:/can-bo/doi-mat-khau";
        }

        existingDoanVien.setMatKhau(encoder.encode(doanVien.getMatKhau()));
        existingDoanVien.setReMatKhau(existingDoanVien.getMatKhau());

        user.setPassword(existingDoanVien.getMatKhau());

        doanVienService.updateDoanVien(existingDoanVien);
        userRepository.save(user);

        return "redirect:/can-bo/danh-sach-doan-vien";
    }

    /*
     * Hiển thị danh sách đoàn viên của chi đoàn cụ thể
     */
    @GetMapping("/can-bo/danh-sach-doan-vien")
    public String listDoanViens(Model model, Principal principal, @Param("keyword") String keyword) {
        if(keyword != null){
            String email = principal.getName();
            idDangNhap = doanVienRepository.findByEmail(email).getId();
            count = 0;

            DoanVien doanVien = doanVienService.getDoanVienById(idDangNhap);
            idChiDoan = chiDoanService.getChiDoanById(doanVienService.getDoanVienById(idDangNhap).getIdChiDoan()).getId();
            List<DoanVien> listDoanViens = doanVienRepository.findByHoTenContaining(keyword);
            List<DoanVien> listDoanViensByName = new ArrayList();
            for(DoanVien dv : listDoanViens){
                if(dv.getIdChiDoan().equals(idChiDoan)){
                    listDoanViensByName.add(dv);
                }
            }
            model.addAttribute("doanvien", doanVien);
            model.addAttribute("tenChiDoan", chiDoanService.getChiDoanById(doanVienService.getDoanVienById(idDangNhap).getIdChiDoan()).getTenChiDoan());
            model.addAttribute("listDoanViens", listDoanViensByName);
            model.addAttribute("currentAccount", doanVienService.getDoanVienById(idDangNhap).getHoTen() + doanVienService.getDoanVienById(idDangNhap).getRole());
            // model.addAttribute("idDoanVien", id);
            idCanBo = idDangNhap;
        }else{
            String email = principal.getName();
            idDangNhap = doanVienRepository.findByEmail(email).getId();
            count = 0;

            DoanVien doanVien = doanVienService.getDoanVienById(idDangNhap);
            idChiDoan = doanVien.getIdChiDoan();
            List<DoanVien> listDoanViens = doanVienRepository.getListDoanVien(doanVien.getIdChiDoan());
            model.addAttribute("doanvien", doanVien);
            model.addAttribute("tenChiDoan", chiDoanService.getChiDoanById(doanVienService.getDoanVienById(idDangNhap).getIdChiDoan()).getTenChiDoan());
            model.addAttribute("listDoanViens", listDoanViens);
            model.addAttribute("currentAccount", doanVienService.getDoanVienById(idDangNhap).getHoTen() + " - " + doanVienService.getDoanVienById(idDangNhap).getChucVu());
            // model.addAttribute("idDoanVien", id);
            idCanBo = idDangNhap;
        }

        return "cb-danh-sach-doan-vien";
    }

    /*
     * Hiển thị giao diện thêm đoàn viên cho cán bộ
     */
    @GetMapping("/can-bo/danh-sach-doan-vien/them-doan-vien")
    public String createDoanVienForm(Model model) {
        DoanVien doanVien = new DoanVien();

        model.addAttribute("doanvien", doanVien);

        if(errMaDoanVien != 0){
            System.out.println("Trùng mã");
            model.addAttribute("errMaDoanVien", "Mã đoàn viên đã tồn tại");
            errMaDoanVien = 0;
            
        }

        if(errEmail != 0){
            System.out.println("Trùng email");
            model.addAttribute("errEmail", "Email đã tồn tại");
            errEmail = 0;
        }

        return "cb-them-doan-vien";
    }

    /*
     * Lưu đoàn viên đã được thêm vào cơ sở dữ liệu
     */
    @PostMapping("/can-bo/danh-sach-doan-vien")
    public String saveDoanVien(@ModelAttribute("doanvien") DoanVien doanVien) {
        if(!doanVienService.ifDoanVienExistedMaDV(doanVien.getMaDoanVien())){
            errMaDoanVien++;
            if(!doanVienService.ifDoanVienExistedEmail(doanVien.getEmail())){
                errEmail++;
                return "redirect:/can-bo/danh-sach-doan-vien/them-doan-vien";
            }
            return "redirect:/can-bo/danh-sach-doan-vien/them-doan-vien";
        }else{
            if(!doanVienService.ifDoanVienExistedEmail(doanVien.getEmail())){
                errEmail++;
                return "redirect:/can-bo/danh-sach-doan-vien/them-doan-vien";
            }
        }

        User user = new User();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(doanVien.getMatKhau());
        doanVien.setMatKhau(encodedPassword);
        doanVien.setReMatKhau(encodedPassword);
        doanVien.setIdChiDoan(idChiDoan);
        doanVien.setTenChiDoan(chiDoanService.getChiDoanById(idChiDoan).getTenChiDoan());

        if (doanVien.getChucVu().equalsIgnoreCase("Đoàn viên")) {
            doanVien.setRole(Role.DOANVIEN);

            // Tách họ tên và lưu vào User
            String fullName = doanVien.getHoTen();
            String[] nameSplited = fullName.split(" ");
            String firstName = nameSplited[0];
            String lastName = fullName.substring(firstName.length()+1).trim();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            // Tạo tài khoản vào User
            user.setEmail(doanVien.getEmail());
            user.setPassword(doanVien.getMatKhau());
            user.setRole(doanVien.getRole());

            userRepository.save(user);
            doanVienService.saveDoanVien(doanVien);

            return "redirect:/can-bo/danh-sach-doan-vien";
        } else {
            doanVien.setRole(Role.CANBO);
            // Tìm xem đoàn viên ứng với chức vụ đó đã tồn tại trong lớp chưa
            DoanVien dv = doanVienRepository.getDoanVienByChucVu(doanVien.getChucVu(), idChiDoan);
            if(dv != null){
                dv.setChucVu("Đoàn viên");
                dv.setRole(Role.DOANVIEN);
                User newUser = userRepository.findByEmail(dv.getEmail());
                newUser.setRole(Role.DOANVIEN);
            }

            // Tách họ tên và lưu vào User
            String fullName = doanVien.getHoTen();
            String[] nameSplited = fullName.split(" ");
            String firstName = nameSplited[0];
            String lastName = fullName.substring(firstName.length()+1).trim();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            // Tạo tài khoản vào User
            user.setEmail(doanVien.getEmail());
            user.setPassword(doanVien.getMatKhau());
            user.setRole(doanVien.getRole());

            userRepository.save(user);
            doanVienService.saveDoanVien(doanVien);
            
            if(dv.getId() == idDangNhap){
                return "redirect:/login";
            }

            return "redirect:/can-bo/danh-sach-doan-vien";
        }

        // // Tìm xem đoàn viên ứng với chức vụ đó đã tồn tại trong lớp chưa
        // DoanVien dv = doanVienRepository.getDoanVienByChucVu(doanVien.getChucVu(), idChiDoan);
        // if(dv != null){
        //     dv.setChucVu("Đoàn viên");
        //     dv.setRole(Role.DOANVIEN);
        //     User newUser = userRepository.findByEmail(dv.getEmail());
        //     newUser.setRole(Role.DOANVIEN);
        // }

        // // Tách họ tên và lưu vào User
        // String fullName = doanVien.getHoTen();
        // String[] nameSplited = fullName.split(" ");
        // String firstName = nameSplited[0];
        // String lastName = fullName.substring(firstName.length()+1).trim();
        // user.setFirstName(firstName);
        // user.setLastName(lastName);
        // // Tạo tài khoản vào User
        // user.setEmail(doanVien.getEmail());
        // user.setPassword(doanVien.getMatKhau());
        // user.setRole(doanVien.getRole());

        // userRepository.save(user);
        // doanVienService.saveDoanVien(doanVien);
        
        // // if(dv.getId() == idDangNhap){
        // //     return "redirect:/login";
        // // }

        // return "redirect:/can-bo/danh-sach-doan-vien";
    }

    /*
     * Xoá đoàn viên của chi đoàn
     */
    @GetMapping("/can-bo/danh-sach-doan-vien/xoa/{id}")
    public String deleteDoanVien(@PathVariable Long id) {
        User user = userRepository.findByEmail(doanVienService.getDoanVienById(id).getEmail());
        // System.out.println(idDangNhap + " " + id);
        if(id == idDangNhap){
            userRepository.delete(user);
            doanVienService.deleteDoanVien(id);
            return "redirect:/login";
        }

        userRepository.delete(user);
        doanVienService.deleteDoanVien(id);

        return "redirect:/can-bo/danh-sach-doan-vien";
    }

    /*
     * Hiển thị giao diện đánh giá đoàn viên
     */
    @GetMapping("/can-bo/danh-sach-doan-vien/danh-gia/{id}")
    public String createDanhGiaForm(@PathVariable Long id, Model model) {
        DanhGia danhGia = new DanhGia();
        idDoanVienDuocDanhGia = id;
        model.addAttribute("danhgia", danhGia);
        model.addAttribute("tenChiDoan", chiDoanService.getChiDoanById(doanVienService.getDoanVienById(idDangNhap).getIdChiDoan()).getTenChiDoan());
        model.addAttribute("doanvien", doanVienService.getDoanVienById(id));

        return "cb-danh-gia-doan-vien";
    }

    /**
     * Thêm đánh giá vào csdl
     */
    @PostMapping("/can-bo/danh-gia-doan-vien")
    public String saveDanhGia(@ModelAttribute("danhgia") DanhGia danhGia) {
        DoanVien existingDoanVien = doanVienService.getDoanVienById(idDoanVienDuocDanhGia);
        danhGia.setMaDoanVien(existingDoanVien.getMaDoanVien());
        danhGia.setHoTen(existingDoanVien.getHoTen());
        danhGia.setNguoiDanhGia(doanVienService.getDoanVienById(idDangNhap).getHoTen());

        danhGiaService.saveDanhGia(danhGia);

        return "redirect:/can-bo/danh-sach-doan-vien";
    }

    // ----------------------------------ĐOÀN VIÊN-------------------------------------------
    /*
     * Hiển thị danh sách đoàn viên của chi đoàn cụ thể
     */
    @GetMapping("/doan-vien/danh-sach-doan-vien")
    public String listDoanViens1(Model model, Principal principal, @Param("keyword") String keyword) {
        if(keyword != null){
            String email = principal.getName();
            idDangNhap = doanVienRepository.findByEmail(email).getId();

            DoanVien doanVien = doanVienService.getDoanVienById(idDangNhap);
            List<DoanVien> listDoanViens = doanVienRepository.findByHoTenContaining(keyword);
            List<DoanVien> listDoanViensByName = new ArrayList();
            for(DoanVien dv : listDoanViens){
                if(dv.getTenChiDoan().equals(doanVien.getTenChiDoan())){
                    listDoanViensByName.add(dv);
                }
            }
            model.addAttribute("tenChiDoan", doanVien.getTenChiDoan());
            model.addAttribute("listDoanViens", listDoanViensByName);
            model.addAttribute("currentAccount", doanVienService.getDoanVienById(idDangNhap).getHoTen() + " - " + doanVienService.getDoanVienById(idDangNhap).getChucVu());
        }else{
            String email = principal.getName();
            idDangNhap = doanVienRepository.findByEmail(email).getId();

            DoanVien doanVien = doanVienService.getDoanVienById(idDangNhap);
            List<DoanVien> listDoanViens = doanVienRepository.getListDoanVien(doanVien.getIdChiDoan());
            model.addAttribute("tenChiDoan", doanVien.getTenChiDoan());
            model.addAttribute("listDoanViens", listDoanViens);
            model.addAttribute("currentAccount", doanVienService.getDoanVienById(idDangNhap).getHoTen() + " - " + doanVienService.getDoanVienById(idDangNhap).getChucVu());
        }

        return "dv-danh-sach-doan-vien";
    }

    /**
     * Hiển thị giao diện cập nhật thông tin
     */
    @GetMapping("/doan-vien/chinh-sua-thong-tin")
    public String updateThongTinForm(Model model) {
        model.addAttribute("doanvien", doanVienService.getDoanVienById(idDangNhap));
        model.addAttribute("tenChiDoan", doanVienService.getDoanVienById(idDangNhap).getTenChiDoan());
        return "dv-chinh-sua-thong-tin";
    }

    /*
     * Cập nhật thông tin cán bộ dựa vào id
     */
    @PostMapping("/doan-vien/thong-tin-doan-vien/{id}")
    public String updateDoanvien(@PathVariable Long id, @ModelAttribute("doanvien") DoanVien doanVien, Model model) {
        DoanVien existingDoanVien = doanVienService.getDoanVienById(id);
        User user = userRepository.findByEmail(existingDoanVien.getEmail());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        // existingDoanVien.setIdChiDoan(chiDoanRepository.findByTenChiDoanContaining(doanVien.getTenChiDoan()).get(0).getId());
        // existingDoanVien.setIdChiDoan(existingDoanVien.getIdChiDoan());
        existingDoanVien.setTenChiDoan(doanVien.getTenChiDoan());
        existingDoanVien.setMaDoanVien(doanVien.getMaDoanVien());
        existingDoanVien.setHoTen(doanVien.getHoTen());
        existingDoanVien.setCccd(doanVien.getCccd());
        existingDoanVien.setQueQuan(doanVien.getQueQuan());
        existingDoanVien.setDanToc(doanVien.getDanToc());
        existingDoanVien.setNgaySinh(doanVien.getNgaySinh());
        existingDoanVien.setSdt(doanVien.getSdt());
        // DoanVien dv = doanVienRepository.getDoanVienByChucVu(doanVien.getChucVu(), doanVien.getTenChiDoan());
        // if (dv != null) {
        //     dv.setChucVu("Đoàn viên");
        //     dv.setRole(Role.DOANVIEN);
        //     User newUser = userRepository.findByEmail(dv.getEmail());
        //     newUser.setRole(Role.DOANVIEN);
        // }
        // existingDoanVien.setChucVu(doanVien.getChucVu());
        // if (doanVien.getChucVu().equalsIgnoreCase("Đoàn viên")) {
        //     existingDoanVien.setRole(Role.DOANVIEN);
        //     user.setRole(Role.DOANVIEN);
        // } else {
        //     existingDoanVien.setRole(Role.CANBO);
        //     user.setRole(Role.CANBO);
        // }
        existingDoanVien.setNgayVaoDoan(doanVien.getNgayVaoDoan());
        // existingDoanVien.setEmail(doanVien.getEmail());
        // existingDoanVien.setMatKhau(encoder.encode(doanVien.getMatKhau()));

        // user.setPassword(existingDoanVien.getMatKhau());
        // userRepository.save(user);

        doanVienService.updateDoanVien(existingDoanVien);
        return "redirect:/doan-vien/danh-sach-doan-vien";
    }

    /**
     * Hiển thị danh sách đánh giá của đoàn viên
     */
    @GetMapping("/doan-vien/danh-gia")
    public String showDanhGia(Model model){
        String tenChiDoan = doanVienService.getDoanVienById(idDangNhap).getTenChiDoan();
        String maDoanVien = doanVienService.getDoanVienById(idDangNhap).getMaDoanVien();
        List<DanhGia> listDanhGias = danhGiaRepository.getListDanhGias(maDoanVien);

        for(DanhGia danhGia : listDanhGias){
            System.out.println(danhGia.getNamHoc() + danhGia.getNguoiDanhGia() + danhGia.getNoiDung());
        }

        model.addAttribute("listDanhGias", listDanhGias);
        model.addAttribute("tenChiDoan", tenChiDoan);
        return "dv-danh-gia";
    }

    /**
     * Hiển thị giao diện đổi mật khẩu
     */
    @GetMapping("/doan-vien/doi-mat-khau")
    public String doiMatKhauDoanVienForm(Model model, Principal principal){
        String email = principal.getName();
        model.addAttribute("doanvien", doanVienRepository.findByEmail(email));
        model.addAttribute("tenChiDoan", doanVienRepository.findByEmail(email).getTenChiDoan());

        if(countSaiPass2 != 0){
            model.addAttribute("errSaiPass", "Mật khẩu chưa chính xác");
            countSaiPass2 = 0;
        }

        return "dv-doi-mat-khau";
    }

    /**
     * Thực hiện đổi mật khẩu
     */
    @PostMapping("/doan-vien/doi-mat-khau")
    public String doiMatKhauDoanVien(Model model, Principal principal, @ModelAttribute("doanvien") DoanVien doanVien){
        String email = principal.getName();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        DoanVien existingDoanVien = doanVienRepository.findByEmail(email);
        User user = userRepository.findByEmail(existingDoanVien.getEmail());

        if(!doanVien.getMatKhau().equals(doanVien.getReMatKhau())){
            countSaiPass2++;
            return "redirect:/doan-vien/doi-mat-khau";
        }
        
        existingDoanVien.setMatKhau(encoder.encode(doanVien.getMatKhau()));
        existingDoanVien.setReMatKhau(existingDoanVien.getMatKhau());
        user.setPassword(existingDoanVien.getMatKhau());

        doanVienService.updateDoanVien(existingDoanVien);
        userRepository.save(user);

        return "redirect:/doan-vien/danh-sach-doan-vien";
    }
}