package io.github.codecube.engine;

import java.util.List;

import org.bukkit.entity.Entity;

import io.github.codecube.creation.HotbarToolbar;
import io.github.codecube.creation.HotbarToolbarItem;
import io.github.codecube.creation.ToggleHTIL;
import io.github.codecube.util.StoneHoeIcons;

public abstract class EntityProp extends Prop {
	private Entity entity;
	private boolean glowing = false, gravity = true, silent = false;

	@Override
	public String getType() {
		return "Entity";
	}

	@Override
	protected void onPositionChange() {
		if (entity != null)
			entity.teleport(worldPosition);
	}

	@Override
	protected HotbarToolbar createToolbar(List<HotbarToolbarItem> extraMiscItems) {
		HotbarToolbarItem toggleGravity = new HotbarToolbarItem(StoneHoeIcons.ICON_GRAVITY);
		toggleGravity.setName("Toggle Gravity");
		toggleGravity.setListener(new ToggleHTIL() {
			@Override
			protected void toggle() {
				setGravity(!hasGravity());
			}
		});
		extraMiscItems.add(0, toggleGravity);

		HotbarToolbarItem toggleGlow = new HotbarToolbarItem(StoneHoeIcons.ICON_OUTLINE);
		toggleGlow.setName("Toggle Glow");
		toggleGlow.setListener(new ToggleHTIL() {
			@Override
			protected void toggle() {
				setGlowing(!isGlowing());
			}
		});
		extraMiscItems.add(1, toggleGlow);

		HotbarToolbar tr = super.createToolbar(extraMiscItems);

		return tr;
	}

	public boolean isGlowing() {
		if (entity == null)
			return glowing;
		else
			return entity.isGlowing();
	}

	public void setGlowing(boolean glowing) {
		this.glowing = glowing;
		if (entity != null)
			entity.setGlowing(glowing);
	}

	public boolean hasGravity() {
		if (entity == null)
			return gravity;
		else
			return entity.hasGravity();
	}

	public void setGravity(boolean gravity) {
		this.gravity = gravity;
		if (entity != null)
			entity.setGravity(gravity);
	}

	public boolean isSilent() {
		if (entity == null)
			return silent;
		else
			return entity.isSilent();
	}

	public void setSilent(boolean silent) {
		this.silent = silent;
		if (entity != null)
			entity.setSilent(silent);
	}

	protected abstract Entity onCreateEntity();

	@Override
	protected final boolean onCreate() {
		entity = onCreateEntity();
		if (entity == null)
			return false;
		entity.setGlowing(glowing);
		entity.setGravity(gravity);
		entity.setSilent(silent);
		return true;
	}

	protected abstract void onDestroyEntity();

	@Override
	protected final boolean onDestroy() {
		if (entity == null)
			return false;
		glowing = entity.isGlowing();
		gravity = entity.hasGravity();
		silent = entity.isSilent();
		onDestroyEntity();
		entity.remove();
		return true;
	}
}
