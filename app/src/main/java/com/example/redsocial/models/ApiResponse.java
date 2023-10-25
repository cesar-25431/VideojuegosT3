package com.example.redsocial.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponse<T> {
    @SerializedName("page")
    public int page;

    @SerializedName("perPage")
    public int perPage;

    @SerializedName("totalItems")
    public int totalItems;

    @SerializedName("totalPages")
    public int totalPages;

    @SerializedName("items")
    public List<T> items;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
