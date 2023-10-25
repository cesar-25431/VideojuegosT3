package com.example.redsocial.models;

import java.util.List;

public class ListPublicationResponse {
    public int page;
    public int perPage;
    public int totalItems;
    public int totalPages;
    public List<Publication> items;

    public class Publication {
        public String collectionId;
        public String collectionName;
        public String created;
        public String descripcion;
        public Expand expand;
        public String id;
        public String imagen;
        public String ubicacion;
        public String updated;
        public String usuario;

        public Publication(String collectionId, String collectionName, String created, String descripcion, Expand expand, String id, String imagen, String ubicacion, String updated, String usuario) {
            this.collectionId = collectionId;
            this.collectionName = collectionName;
            this.created = created;
            this.descripcion = descripcion;
            this.expand = expand;
            this.id = id;
            this.imagen = imagen != null ? "http://146.190.60.137:8090/api/files/publicacion/" + id + "/" +imagen : "";
            this.ubicacion = ubicacion;
            this.updated = updated;
            this.usuario = usuario;
        }

        @Override
        public String toString() {
            return "Publication{" +
                    "collectionId='" + collectionId + '\'' +
                    ", collectionName='" + collectionName + '\'' +
                    ", created='" + created + '\'' +
                    ", descripcion='" + descripcion + '\'' +
                    ", expand=" + expand +
                    ", id='" + id + '\'' +
                    ", imagen='" + imagen + '\'' +
                    ", ubicacion='" + ubicacion + '\'' +
                    ", updated='" + updated + '\'' +
                    ", usuario='" + usuario + '\'' +
                    '}';
        }
    }

    public class Expand {
        public Usuario usuario;

        @Override
        public String toString() {
            return "Expand{" +
                    "usuario=" + usuario +
                    '}';
        }
    }

    public class Usuario {
        public String avatar;
        public String collectionId;
        public String collectionName;
        public String created;
        public boolean emailVisibility;
        public String id;
        public String updated;
        public String username;
        public boolean verified;

        public Usuario(String avatar, String collectionId, String collectionName, String created, boolean emailVisibility, String id, String updated, String username, boolean verified) {
            this.avatar = avatar != null ? "http://146.190.60.137:8090/api/files/users_red_social/" + id + "/" +avatar : "";
            this.collectionId = collectionId;
            this.collectionName = collectionName;
            this.created = created;
            this.emailVisibility = emailVisibility;
            this.id = id;
            this.updated = updated;
            this.username = username;
            this.verified = verified;
        }

        @Override
        public String toString() {
            return "Usuario{" +
                    "avatar='" + avatar + '\'' +
                    ", collectionId='" + collectionId + '\'' +
                    ", collectionName='" + collectionName + '\'' +
                    ", created='" + created + '\'' +
                    ", emailVisibility=" + emailVisibility +
                    ", id='" + id + '\'' +
                    ", updated='" + updated + '\'' +
                    ", username='" + username + '\'' +
                    ", verified=" + verified +
                    '}';
        }
    }


    @Override
    public String toString() {
        return "ListPublicationResponse{" +
                "page=" + page +
                ", perPage=" + perPage +
                ", totalItems=" + totalItems +
                ", totalPages=" + totalPages +
                ", items=" + items +
                '}';
    }
}
