package util;

import model.Usuario; // Importa a sua classe Usuario

public class UsuarioSessao {

    // A instância estática que armazena o usuário atualmente logado
    private static Usuario usuarioLogado = null;

    // Define qual usuário está logado
    public static void setUsuarioLogado(Usuario usuario) {
        usuarioLogado = usuario;
    }

    // Retorna o objeto do usuário logado (pode ser null)
    public static Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    // Limpa a sessão (Logout)
    public static void limpar() {
        usuarioLogado = null;
    }
}