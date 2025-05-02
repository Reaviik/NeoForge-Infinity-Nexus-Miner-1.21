package com.Infinity.Nexus.Miner.networking;

import com.Infinity.Nexus.Miner.InfinityNexusMiner;
import com.Infinity.Nexus.Miner.networking.packet.AssembleMinerC2SPacket;
import com.Infinity.Nexus.Miner.networking.packet.EnergySyncS2CPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
public class ModMessages {
//    private static SimpleChannel INSTANCE;
//    private static int packetId = 0;
//    private static int id() {
//        return packetId++;
//    }
//    public static void register() {
//        SimpleChannel net = NetworkRegistry.ChannelBuilder
//                .named(new ResourceLocation(InfinityNexusMiner.MOD_ID, "messages"))
//                .networkProtocolVersion(() -> "1.0")
//                .clientAcceptedVersions(s -> true)
//                .serverAcceptedVersions(s -> true)
//                .simpleChannel();
//        INSTANCE = net;
//
//        net.messageBuilder(EnergySyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
//                .decoder(EnergySyncS2CPacket::new)
//                .encoder(EnergySyncS2CPacket::toBytes)
//                .consumerMainThread(EnergySyncS2CPacket::handle)
//                .add();
//
//        net.messageBuilder(AssembleMinerC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
//                .decoder(AssembleMinerC2SPacket::new)
//                .encoder(AssembleMinerC2SPacket::toBytes)
//                .consumerMainThread(AssembleMinerC2SPacket::handle)
//                .add();
//    }
//
//    public static <MSG> void sendToServer(MSG message) {
//        INSTANCE.sendToServer(message);
//    }
//    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
//        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
//    }
//
//    public static <MSG> void sendToClients(MSG message) {
//        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
//    }

}