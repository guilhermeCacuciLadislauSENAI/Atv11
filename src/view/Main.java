package view;

import dao.*;
import model.*;
import java.util.List;
import java.util.Scanner;
import util.UsuarioSessao;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private static final PostagemDAO postagemDAO = new PostagemDAO();
    private static final CurtidaDAO curtidaDAO = new CurtidaDAO();
    private static final DirectDAO directDAO = new DirectDAO();

    public static void main(String[] args) {
        boolean rodando = true;
        System.out.println("--- BEM-VINDO AO MINITOK (Rede Social) ---");

        while (rodando) {

            // --- MENSAGEM DE SESSÃO ---
            if (UsuarioSessao.getUsuarioLogado() != null) {
                System.out.println("\nLogado como: " + UsuarioSessao.getUsuarioLogado().getNome() + " (ID: " + UsuarioSessao.getUsuarioLogado().getId() + ")");
            } else {
                System.out.println("\nATENÇÃO: Usuário não logado.");
            }

            exibirMenu();

            if (!scanner.hasNextInt()) {
                System.out.println("\nEntrada invalida. Digite um numero de 1 a 15.");
                scanner.nextLine();
                continue;
            }

            int opcao = scanner.nextInt();
            scanner.nextLine();

            // ---------------------------------------------------
            // BLOQUEIO DE OPÇÕES SE NÃO ESTIVER LOGADO
            // Permite apenas: Cadastrar (1), Login (14) e Sair (13)
            // ---------------------------------------------------
            if (UsuarioSessao.getUsuarioLogado() == null && !(opcao == 14 || opcao == 13 || opcao == 1)) {
                System.out.println("\nVocê precisa estar logado para acessar esta opção!");
                System.out.println("Use a opção 14 para realizar login.");
                continue;
            }

            switch (opcao) {
                case 1:
                    criarUsuario();
                    break;
                case 2:
                    listarUsuarios();
                    break;
                case 3:
                    atualizarUsuario();
                    break;
                case 4:
                    excluirUsuario();
                    break;
                case 5:
                    criarPostagem();
                    break;
                case 6:
                    listarPostagensDeUsuario();
                    break;
                case 7:
                    excluirPostagem();
                    break;
                case 8:
                    curtirPostagem();
                    break;
                case 9:
                    descurtirPostagem();
                    break;
                case 10:
                    mostrarQuantidadeDeCurtidas();
                    break;
                case 11:
                    enviarDirect();
                    break;
                case 12:
                    listarDirectsEntreUsuarios();
                    break;
                case 13:
                    rodando = false;
                    System.out.println("Programa encerrado. Ate mais!");
                    break;
                case 14:
                    realizarLogin();
                    break;
                case 15:
                    realizarLogout();
                    break;
                default:
                    System.out.println("\nOpcao invalida. Digite um numero de 1 a 15.");
            }
            System.out.println("----------------------------------------");
        }
        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("\n*** MENU ***");
        System.out.println("1. Criar usuario (C)");
        System.out.println("2. Listar usuarios (R)");
        System.out.println("3. Atualizar usuario (U)");
        System.out.println("4. Excluir usuario (D)");
        System.out.println("--- Postagens ---");
        System.out.println("5. Criar postagem (C)");
        System.out.println("6. Listar postagens de um usuario (R)");
        System.out.println("7. Excluir postagem (D)");
        System.out.println("--- Interacao ---");
        System.out.println("8. Curtir postagem");
        System.out.println("9. Descurtir postagem");
        System.out.println("10. Mostrar quantidade de curtidas");
        System.out.println("11. Enviar Direct");
        System.out.println("12. Listar Directs entre dois usuarios");
        System.out.println("--- Sessao ---");
        System.out.println("14. Login");
        System.out.println("15. Logout");
        System.out.println("13. Sair");
        System.out.print("Escolha uma opcao: ");
    }

    // -------------------------------------------------------------------
    // NOVOS MÉTODOS DE SESSÃO
    // -------------------------------------------------------------------
    /**
     * Gerencia a tentativa de login do usuário, usando o DAO e armazenando na sessão.
     */
    private static void realizarLogin() {
        if (UsuarioSessao.getUsuarioLogado() != null) {
            System.out.println("Você já está logado como " + UsuarioSessao.getUsuarioLogado().getNome() + ".");
            return;
        }

        System.out.println("\n-- Realizar Login --");
        System.out.print("Email: ");
        String emailLog = scanner.nextLine();

        System.out.print("Senha: ");
        String senhaLog = scanner.nextLine();

        // A chamada ao DAO retorna o objeto Usuario completo
        Usuario logado = usuarioDAO.login(emailLog, senhaLog);

        if (logado != null) {
            UsuarioSessao.setUsuarioLogado(logado); // Armazena na sessão
            System.out.println("Login OK! Bem-vindo(a), " + logado.getNome());
        } else {
            System.out.println("Login inválido. Verifique email e senha.");
        }
    }

    /**
     * Limpa a sessão atual do usuário.
     */
    private static void realizarLogout() {
        if (UsuarioSessao.getUsuarioLogado() != null) {
            System.out.println("Logout realizado: " + UsuarioSessao.getUsuarioLogado().getNome());
            UsuarioSessao.limpar(); // Limpa a sessão
        } else {
            System.out.println("Você não está logado.");
        }
    }


    // -------------------------------------------------------------------
    // 1. Criar usuario
    // -------------------------------------------------------------------
    private static void criarUsuario() {
        System.out.println("\n-- Criar Usuario --");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        if (email.trim().isEmpty()) {
            System.out.println("Erro: O email nao pode ser vazio.");
            return;
        }

        Usuario novoUsuario = new Usuario(nome, email, senha);

        if (usuarioDAO.cadastrarUsuario(novoUsuario)) {
            System.out.println("Usuario criado com sucesso!");
        } else {
            System.out.println("Falha ao criar usuario (Verifique se o email ja existe).");
        }
    }

    // -------------------------------------------------------------------
    // 2. Listar usuarios
    // -------------------------------------------------------------------
    private static void listarUsuarios() {
        System.out.println("\n-- Lista de Usuarios --");
        List<Usuario> usuarios = usuarioDAO.listarTodos();

        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuario cadastrado.");
            return;
        }

        for (Usuario u : usuarios) {
            System.out.printf("ID: %d | Nome: %s | Email: %s\n", u.getId(), u.getNome(), u.getEmail());
        }
    }

    // -------------------------------------------------------------------
    // 3. Atualizar usuario
    // -------------------------------------------------------------------
    private static void atualizarUsuario() {
        System.out.println("\n-- Atualizar Usuario --");
        System.out.print("ID do usuario a atualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Usuario usuarioExistente = usuarioDAO.exibirInformacoes(id);
        if (usuarioExistente == null) {
            System.out.println("Usuario com ID " + id + " nao encontrado.");
            return;
        }

        System.out.println("Usuario atual: " + usuarioExistente.getNome() + " (" + usuarioExistente.getEmail() + ")");

        System.out.print("Novo Nome (ou Enter para manter): ");
        String novoNome = scanner.nextLine();
        if (!novoNome.trim().isEmpty()) {
            usuarioExistente.setNome(novoNome);
        }

        System.out.print("Novo Email (ou Enter para manter): ");
        String novoEmail = scanner.nextLine();
        if (!novoEmail.trim().isEmpty()) {
            usuarioExistente.setEmail(novoEmail);
        }

        System.out.print("Nova Senha (ou Enter para manter): ");
        String novaSenha = scanner.nextLine();
        if (!novaSenha.trim().isEmpty()) {
            usuarioExistente.setSenha(novaSenha);
        }

        if (usuarioDAO.atualizarUsuario(usuarioExistente)) {
            System.out.println("Usuario atualizado com sucesso!");
        } else {
            System.out.println("Falha ao atualizar usuario (Verifique o novo email).");
        }
    }

    // -------------------------------------------------------------------
    // 4. Excluir usuario
    // -------------------------------------------------------------------
    private static void excluirUsuario() {
        System.out.println("\n-- Excluir Usuario --");
        System.out.print("ID do usuario a excluir: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        if (usuarioDAO.excluirUsuario(id)) {
            System.out.println("Usuario ID " + id + " excluido com sucesso.");
        } else {
            System.out.println("Falha ao excluir usuario.");
        }
    }

    // -------------------------------------------------------------------
    // 5. Criar postagem (Usa o ID da Sessão)
    // -------------------------------------------------------------------
    private static void criarPostagem() {
        System.out.println("\n-- Criar Postagem --");
        // Usa o ID do usuário logado diretamente
        int usuarioId = UsuarioSessao.getUsuarioLogado().getId();

        System.out.print("Conteudo da postagem: ");
        String conteudo = scanner.nextLine();

        if (conteudo.trim().isEmpty()) {
            System.out.println("Erro: O conteudo da postagem nao pode ser vazio.");
            return;
        }

        Postagem novaPostagem = new Postagem(usuarioId, conteudo);

        if (postagemDAO.criarPostagem(novaPostagem)) {
            System.out.println("Postagem criada com sucesso pelo ID " + usuarioId + "!");
        } else {
            System.out.println("Falha ao criar postagem.");
        }
    }

    // -------------------------------------------------------------------
    // 6. Listar postagens de um usuario
    // -------------------------------------------------------------------
    private static void listarPostagensDeUsuario() {
        System.out.println("\n-- Listar Postagens de um Usuario --");
        System.out.print("ID do Usuario para listar postagens: ");
        int usuarioId = scanner.nextInt();
        scanner.nextLine();

        List<Postagem> postagens = postagemDAO.listarPostagensPorUsuario(usuarioId);

        if (postagens.isEmpty()) {
            System.out.println("O Usuario ID " + usuarioId + " nao possui postagens.");
            return;
        }

        System.out.println("\nPostagens do Usuario ID " + usuarioId + ":");
        for (Postagem p : postagens) {
            System.out.printf("ID Post: %d | Data: %s | Conteudo: %s\n",
                    p.getId(), p.getDataPostagem(), p.getConteudo());
        }
    }

    // -------------------------------------------------------------------
    // 7. Excluir postagem
    // -------------------------------------------------------------------
    private static void excluirPostagem() {
        System.out.println("\n-- Excluir Postagem --");
        System.out.print("ID da postagem a excluir: ");
        int postagemId = scanner.nextInt();
        scanner.nextLine();

        if (postagemDAO.excluirPostagem(postagemId)) {
            System.out.println("Postagem ID " + postagemId + " excluida com sucesso.");
        } else {
            System.out.println("Falha ao excluir postagem. O ID pode nao existir.");
        }
    }

    // -------------------------------------------------------------------
    // 8. Curtir postagem (Usa o ID da Sessão)
    // -------------------------------------------------------------------
    private static void curtirPostagem() {
        System.out.println("\n-- Curtir Postagem --");
        // Usa o ID do usuário logado
        int usuarioId = UsuarioSessao.getUsuarioLogado().getId();

        System.out.print("ID da Postagem a curtir: ");
        int postagemId = scanner.nextInt();
        scanner.nextLine();

        Curtida novaCurtida = new Curtida(postagemId, usuarioId);

        if (curtidaDAO.curtir(novaCurtida)) {
            System.out.println("Postagem ID " + postagemId + " curtida por Usuario ID " + usuarioId + ".");
        } else {
            System.out.println("Falha ao curtir. (Pode ser curtida duplicada).");
        }
    }

    // -------------------------------------------------------------------
    // 9. Descurtir postagem (Usa o ID da Sessão)
    // -------------------------------------------------------------------
    private static void descurtirPostagem() {
        System.out.println("\n-- Descurtir Postagem --");
        // Usa o ID do usuário logado
        int usuarioId = UsuarioSessao.getUsuarioLogado().getId();

        System.out.print("ID da Postagem a descurtir: ");
        int postagemId = scanner.nextInt();
        scanner.nextLine();

        if (curtidaDAO.descurtir(postagemId, usuarioId)) {
            System.out.println("Curtida removida com sucesso!");
        } else {
            System.out.println("Falha ao descurtir. Nenhuma curtida encontrada.");
        }
    }

    // -------------------------------------------------------------------
    // 10. Mostrar quantidade de curtidas
    // -------------------------------------------------------------------
    private static void mostrarQuantidadeDeCurtidas() {
        System.out.println("\n-- Contar Curtidas --");
        System.out.print("ID da Postagem para contar curtidas: ");
        int postagemId = scanner.nextInt();
        scanner.nextLine();

        int total = curtidaDAO.contarCurtidas(postagemId);

        System.out.println("A Postagem ID " + postagemId + " tem " + total + " curtidas.");
    }

    // -------------------------------------------------------------------
    // 11. Enviar Direct (Usa o ID da Sessão)
    // -------------------------------------------------------------------
    private static void enviarDirect() {
        System.out.println("\n-- Enviar Direct --");
        // Usa o ID do remetente logado
        int remetenteId = UsuarioSessao.getUsuarioLogado().getId();

        System.out.print("ID do Destinatario: ");
        int destinatarioId = scanner.nextInt();
        scanner.nextLine();

        if (remetenteId == destinatarioId) {
            System.out.println("Erro: Nao e possivel enviar uma mensagem Direct para si mesmo.");
            return;
        }

        System.out.print("Mensagem: ");
        String mensagem = scanner.nextLine();

        Direct novoDirect = new Direct(remetenteId, destinatarioId, mensagem);

        if (directDAO.enviarMensagem(novoDirect)) {
            System.out.println("Direct enviado com sucesso!");
        } else {
            System.out.println("Falha ao enviar Direct.");
        }
    }

    // -------------------------------------------------------------------
    // 12. Listar Directs entre dois usuarios
    // -------------------------------------------------------------------
    private static void listarDirectsEntreUsuarios() {
        System.out.println("\n-- Listar Directs Entre Usuarios --");
        System.out.print("ID do primeiro usuario (Usuario 1): ");
        int usuario1Id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("ID do segundo usuario (Usuario 2): ");
        int usuario2Id = scanner.nextInt();
        scanner.nextLine();

        if (usuario1Id == usuario2Id) {
            System.out.println("Erro: Os IDs de usuario devem ser diferentes.");
            return;
        }

        List<Direct> mensagens = directDAO.listarMensagensEntreUsuarios(usuario1Id, usuario2Id);

        if (mensagens.isEmpty()) {
            System.out.println("\nNenhuma conversa encontrada.");
            return;
        }

        System.out.println("\n--- Conversa (Ordem Cronologica) ---");
        for (Direct d : mensagens) {
            System.out.printf("[%s] ID %d para ID %d: %s\n",
                    d.getDataEnvio(), d.getRemetenteId(), d.getDestinatarioId(), d.getMensagem());
        }
        System.out.println("------------------------------------");
    }
}