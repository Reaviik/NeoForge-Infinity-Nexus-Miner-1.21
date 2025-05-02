package com.Infinity.Nexus.Miner.command;

import com.Infinity.Nexus.Miner.config.Config;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class Reload {
    public Reload(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("InfinityNexusMiner").then(Commands.literal("reload")
                .executes(this::execute)));
    }

    private int execute(CommandContext<CommandSourceStack> context) {
        if(context.getSource().hasPermission(3)) {
            context.getSource().sendSystemMessage(Component.literal("§bReloading..."));
            Config.reloadConfig(context.getSource());
        }else{
            context.getSource().sendSystemMessage(Component.literal("§cYou don't have permission to use this command"));
        }
        return 1;
    }

}