package com.example.BidBackend.service;

import com.example.BidBackend.model.Admin;

public interface AdminService {
    Admin save(Admin a);
    Admin findById (long id);
}
