package com.technical.test.utils;

import java.util.ArrayList;
import java.util.List;
import com.technical.test.entity.Usuario;
import com.technical.test.entity.dto.RolDto;
import com.technical.test.entity.dto.UsuarioDto;

public class UsuarioMapp {

    public static UsuarioDto toUsuarioDto(Usuario usuario) {
        List<RolDto> idRole = new ArrayList<>();
        return new UsuarioDto(usuario.getId(), usuario.getNombre(), usuario.getAPaterno(), usuario.getAMaterno(),
                idRole);
    }

    public static Usuario toUsuario(UsuarioDto usuarioDto) {
        return new Usuario(usuarioDto.getId(), usuarioDto.getNombre(), usuarioDto.getApellidoPaterno(),
                usuarioDto.getApellidoMaterno(), 0);
    }

    public static List<UsuarioDto> toUsuarioDtoList(List<Usuario> usuarios) {
        return usuarios.stream().map(UsuarioMapp::toUsuarioDto).toList();
    }

    public static List<Usuario> toUsuarioList(List<UsuarioDto> usuariosDto) {
        return usuariosDto.stream().map(UsuarioMapp::toUsuario).toList();
    }

}
