package me.therealfickle.rabid.block;

import com.mojang.serialization.MapCodec;
import me.therealfickle.rabid.block.entity.FicliumCrateBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.Nullable;

public class FicliumCrateBlock extends BaseEntityBlock {
	public static final MapCodec<FicliumCrateBlock> CODEC = simpleCodec(FicliumCrateBlock::new);
	public static final EnumProperty<Direction> FACING = BlockStateProperties.FACING;

	@Override
	public MapCodec<FicliumCrateBlock> codec() {
		return CODEC;
	}

	public FicliumCrateBlock(BlockBehaviour.Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
		if (level instanceof ServerLevel serverLevel && level.getBlockEntity(blockPos) instanceof FicliumCrateBlockEntity crate) {
			player.openMenu(crate);
			// TODO Use custom stat later
			player.awardStat(Stats.OPEN_BARREL);
			PiglinAi.angerNearbyPiglins(serverLevel, player, true);
		}

		return InteractionResult.SUCCESS;
	}

	@Override
	protected void affectNeighborsAfterRemoval(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, boolean bl) {
		Containers.updateNeighboursAfterDestroy(blockState, serverLevel, blockPos);
	}

	@Override
	protected void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
		BlockEntity blockEntity = serverLevel.getBlockEntity(blockPos);
		if (blockEntity instanceof FicliumCrateBlockEntity crate) {
			crate.recheckOpen();
		}
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new FicliumCrateBlockEntity(blockPos, blockState);
	}

	@Override
	protected boolean hasAnalogOutputSignal(BlockState blockState) {
		return true;
	}

	@Override
	protected int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos blockPos, Direction direction) {
		return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(level.getBlockEntity(blockPos));
	}

	@Override
	protected BlockState rotate(BlockState blockState, Rotation rotation) {
		return blockState.setValue(FACING, rotation.rotate(blockState.getValue(FACING)));
	}

	@Override
	protected BlockState mirror(BlockState blockState, Mirror mirror) {
		return blockState.rotate(mirror.getRotation(blockState.getValue(FACING)));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
		return this.defaultBlockState().setValue(FACING, blockPlaceContext.getNearestLookingDirection().getOpposite());
	}
}
