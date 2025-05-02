package com.Infinity.Nexus.Miner.networking.packet;

import com.Infinity.Nexus.Miner.block.entity.MinerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

import java.util.function.Supplier;

public class AssembleMinerC2SPacket {
    private final BlockPos pos;

    public AssembleMinerC2SPacket(BlockPos pos) {
        this.pos = pos;
    }

    public AssembleMinerC2SPacket(FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
    }

    //public boolean handle(Supplier<NetworkEvent.Context> supplier) {
    //    NetworkEvent.Context context = supplier.get();
    //    context.enqueueWork(() -> {
    //        ServerPlayer player = context.getSender();
    //        if (player == null) return;
//
    //        ServerLevel level = player.serverLevel();
    //        if (level.getBlockEntity(pos) instanceof MinerBlockEntity blockEntity) {
    //            blockEntity.makeStructure();
    //            blockEntity.setChanged();
    //            blockEntity.makeStructure();
    //            blockEntity.setChanged();
    //            level.sendBlockUpdated(pos, blockEntity.getBlockState(), blockEntity.getBlockState(), 3);
    //        }
    //    });
    //    return true;
    //}
}