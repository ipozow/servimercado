package me.sc.mercado;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

public class Commands implements CommandExecutor {
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        
        if(command.getName().equalsIgnoreCase("mercado")) {
            
            if(sender instanceof Player) {
                
                Player player = (Player) sender;
                
                if(args.length < 1) {
                    
                    player.sendMessage("");
                    player.sendMessage(ChatColor.GOLD + "SERVIMERCADO COMANDOS");
                    player.sendMessage("");
                    
                    player.sendMessage(ChatColor.WHITE + "/mercado nuevo <nombre>");
                    player.sendMessage(ChatColor.AQUA + "Crea una organización.");
                    player.sendMessage("");
                    
                    player.sendMessage(ChatColor.WHITE + "/mercado invitar <organización> <usuario>");
                    player.sendMessage(ChatColor.AQUA + "Invita a un usuario a tu organización.");
                    player.sendMessage("");
                    
                    player.sendMessage(ChatColor.WHITE + "/mercado aceptar <organización>");
                    player.sendMessage(ChatColor.AQUA + "Acepta la invitación a una organización.");
                    player.sendMessage("");
                    
                    player.sendMessage(ChatColor.WHITE + "/mercado rechazar <organización>");
                    player.sendMessage(ChatColor.AQUA + "Rechaza la invitación a una organización.");
                    player.sendMessage("");
                    
                    player.sendMessage(ChatColor.WHITE + "/mercado proyecto <organización> <nombre> <precio> <plazo>");
                    player.sendMessage(ChatColor.AQUA + "Crea un proyecto para contratar trabajadores.");
                    player.sendMessage(ChatColor.GREEN + "Nota: Debes incluir el precio que pagarás y el plazo del projecto en horas.");
                    player.sendMessage("");
                    
                    player.sendMessage(ChatColor.WHITE + "/mercado proyectos");
                    player.sendMessage(ChatColor.AQUA + "Ver los proyectos en las organizaciones en que estás.");
                    player.sendMessage("");
                    
                    player.sendMessage(ChatColor.WHITE + "/mercado postular <organización> <nombre> <tiempo>");
                    player.sendMessage(ChatColor.AQUA + "Postular a un proyecto de alguna organización.");
                    player.sendMessage(ChatColor.GREEN + "Nota: Debes agregar el tiempo que te tomará máximo en horas.");
                    player.sendMessage("");
                    
                    player.sendMessage(ChatColor.WHITE + "/mercado cancelar <organización> <nombre>");
                    player.sendMessage(ChatColor.AQUA + "Cancelar la postulación a un trabajo.");
                    player.sendMessage(ChatColor.GREEN + "Nota: Tendras que pagar según el tiempo transcurrido.");
                    player.sendMessage("");
                    
                    player.sendMessage(ChatColor.WHITE + "/mercado info <organización>");
                    player.sendMessage(ChatColor.AQUA + "Ver la información de una organización.");
                    player.sendMessage("");
                    
                    return true;
                } else {
                    
                    player.sendMessage(ChatColor.RED + "Subcomando inválido!");
                    return true;
                }
            
        } else {
                
                sender.sendMessage(ChatColor.RED + "Solo jugadores pueden usar este comando.");
                return true;
                
        }
            
        }
        return true;
    }
    
}
