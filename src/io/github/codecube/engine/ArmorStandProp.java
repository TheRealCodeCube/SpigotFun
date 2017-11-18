package io.github.codecube.engine;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

public class ArmorStandProp extends EntityProp {
	private ArmorStand armorStand = null;
	private boolean baseVisible = true, structureVisible = true, armsVisible = true, small = false;
	private ItemStack head = null, chest = null, legs = null, feet = null, hand = null;
	private EulerAngle leftArmPose = new EulerAngle(0, 0, 0), rightArmPose = new EulerAngle(0, 0, 0),
			leftLegPose = new EulerAngle(0, 0, 0), rightLegPose = new EulerAngle(0, 0, 0),
			headPose = new EulerAngle(0, 0, 0), bodyPose = new EulerAngle(0, 0, 0);

	public ArmorStandProp() {
		super();
		setGravity(false);
	}

	public boolean isBaseVisible() {
		if (armorStand == null)
			return baseVisible;
		else
			return armorStand.hasBasePlate();
	}

	public void setBaseVisible(boolean baseVisible) {
		this.baseVisible = baseVisible;
		if (armorStand != null)
			armorStand.setBasePlate(baseVisible);
	}

	public boolean isStructureVisible() {
		if (armorStand == null)
			return structureVisible;
		else
			return armorStand.isVisible();
	}

	public void setStructureVisible(boolean structureVisible) {
		this.structureVisible = structureVisible;
		if (armorStand != null) {
			armorStand.setVisible(structureVisible);
			// If marker is set, only worn equipment will be outlined with glow.
			armorStand.setMarker(!structureVisible);
		}
	}

	public boolean isArmsVisible() {
		if (armorStand == null)
			return armsVisible;
		else
			return armorStand.hasArms();
	}

	public void setArmsVisible(boolean armsVisible) {
		this.armsVisible = armsVisible;
		if (armorStand != null)
			armorStand.setArms(armsVisible);
	}

	public boolean isSmall() {
		if (armorStand == null)
			return small;
		else
			return armorStand.isSmall();
	}

	public void setSmall(boolean small) {
		this.small = small;
		if (armorStand != null)
			armorStand.setSmall(small);
	}

	public ItemStack getHead() {
		if (armorStand == null)
			return head;
		else
			return armorStand.getHelmet();
	}

	public void setHead(ItemStack head) {
		this.head = head;
		if (armorStand != null)
			armorStand.setHelmet(head);
	}

	public ItemStack getChest() {
		if (armorStand == null)
			return chest;
		else
			return armorStand.getChestplate();
	}

	public void setChest(ItemStack chest) {
		this.chest = chest;
		if (armorStand != null)
			armorStand.setChestplate(chest);
	}

	public ItemStack getLegs() {
		if (armorStand == null)
			return legs;
		else
			return armorStand.getLeggings();
	}

	public void setLegs(ItemStack legs) {
		this.legs = legs;
		if (armorStand != null)
			armorStand.setLeggings(legs);
	}

	public ItemStack getFeet() {
		if (armorStand == null)
			return feet;
		else
			return armorStand.getBoots();
	}

	public void setFeet(ItemStack feet) {
		this.feet = feet;
		if (armorStand != null)
			armorStand.setBoots(feet);
	}

	public ItemStack getHand() {
		if (armorStand == null)
			return hand;
		else
			return armorStand.getItemInHand();
	}

	public void setHand(ItemStack hand) {
		this.hand = hand;
		if (armorStand != null)
			armorStand.setItemInHand(hand);
	}

	public EulerAngle getLeftArmPose() {
		if (armorStand == null)
			return leftArmPose;
		else
			return armorStand.getLeftArmPose();
	}

	public void setLeftArmPose(EulerAngle leftArmPose) {
		this.leftArmPose = leftArmPose;
		if (armorStand != null)
			armorStand.setLeftArmPose(leftArmPose);
	}

	public EulerAngle getRightArmPose() {
		if (armorStand == null)
			return rightArmPose;
		else
			return armorStand.getRightArmPose();
	}

	public void setRightArmPose(EulerAngle rightArmPose) {
		this.rightArmPose = rightArmPose;
		if (armorStand != null)
			armorStand.setRightArmPose(rightArmPose);
	}

	public EulerAngle getLeftLegPose() {
		if (armorStand == null)
			return leftLegPose;
		else
			return armorStand.getLeftLegPose();
	}

	public void setLeftLegPose(EulerAngle leftLegPose) {
		this.leftLegPose = leftLegPose;
		if (armorStand != null)
			armorStand.setLeftLegPose(leftLegPose);
	}

	public EulerAngle getRightLegPose() {
		if (armorStand == null)
			return rightLegPose;
		else
			return armorStand.getRightLegPose();
	}

	public void setRightLegPose(EulerAngle rightLegPose) {
		this.rightLegPose = rightLegPose;
		if (armorStand != null)
			armorStand.setRightLegPose(rightLegPose);
	}

	public EulerAngle getHeadPose() {
		if (armorStand == null)
			return headPose;
		else
			return armorStand.getHeadPose();
	}

	public void setHeadPose(EulerAngle headPose) {
		this.headPose = headPose;
		if (armorStand != null)
			armorStand.setHeadPose(headPose);
	}

	public EulerAngle getBodyPose() {
		if (armorStand == null)
			return bodyPose;
		else
			return armorStand.getBodyPose();
	}

	public void setBodyPose(EulerAngle bodyPose) {
		this.bodyPose = bodyPose;
		if (armorStand != null)
			armorStand.setBodyPose(bodyPose);
	}

	@Override
	protected Entity onCreateEntity() {
		Entity spawned = worldPosition.getWorld().spawnEntity(worldPosition, EntityType.ARMOR_STAND);
		if (spawned == null)
			return null;
		if (!(spawned instanceof ArmorStand))
			return null;
		armorStand = (ArmorStand) spawned;
		armorStand.setArms(armsVisible);
		armorStand.setBasePlate(baseVisible);
		armorStand.setBodyPose(bodyPose);
		armorStand.setBoots(feet);
		armorStand.setChestplate(chest);
		armorStand.setHeadPose(headPose);
		armorStand.setHelmet(head);
		armorStand.setItemInHand(hand);
		armorStand.setLeftArmPose(leftArmPose);
		armorStand.setLeftLegPose(leftLegPose);
		armorStand.setMarker(!structureVisible); // Makes it so that when it glows, the structure does not glow too.
		armorStand.setRightArmPose(rightArmPose);
		armorStand.setRightLegPose(rightLegPose);
		armorStand.setSmall(small);
		armorStand.setVisible(structureVisible);
		return armorStand;
	}

	@Override
	protected void onDestroyEntity() {
		armsVisible = armorStand.hasArms();
		baseVisible = armorStand.hasBasePlate();
		bodyPose = armorStand.getBodyPose();
		feet = armorStand.getBoots();
		chest = armorStand.getChestplate();
		headPose = armorStand.getHeadPose();
		head = armorStand.getHelmet();
		hand = armorStand.getItemInHand();
		leftArmPose = armorStand.getLeftArmPose();
		leftLegPose = armorStand.getLeftLegPose();
		rightArmPose = armorStand.getRightArmPose();
		rightLegPose = armorStand.getRightLegPose();
		small = armorStand.isSmall();
		structureVisible = armorStand.isVisible();
	}
}
