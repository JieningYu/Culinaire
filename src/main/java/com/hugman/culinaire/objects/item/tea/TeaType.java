package com.hugman.culinaire.objects.item.tea;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TeaType {
	private final Strength strength;
	private final Flavor flavor;

	public TeaType(String strength, String flavor) {
		this(Strength.byName(strength), Flavor.byName(flavor));
	}

	public TeaType(Strength strength, Flavor flavor) {
		this.strength = strength;
		this.flavor = flavor;
	}

	public boolean isCorrect() {
		return this.getStrength() != null && this.getFlavor() != null;
	}

	public Strength getStrength() {
		return strength;
	}

	public Flavor getFlavor() {
		return flavor;
	}

	public TagKey<Item> getTag() {
		return TagKey.of(Registry.ITEM_KEY, new Identifier("c", "tea_ingredients/" + getFlavor().getName() + "/" + getStrength().getName()));
	}

	public int getBrewTime() {
		return flavor.getBrewTime() * strength.getPotency();
	}

	@Override
	public String toString() {
		return "TeaType{" + "strength=" + strength + ", flavor=" + flavor + '}';
	}

	public enum Flavor {
		SWEET("sweet", 9523743,  StatusEffects.SATURATION, true),
		UMAMI("umami", 10059295, StatusEffects.RESISTANCE, true),
		SALTY("salty", 10251038, StatusEffects.SPEED, true),
		SOUR("sour", 7238946, StatusEffects.POISON, false),
		BITTER("bitter", 5057061, StatusEffects.BLINDNESS, false),
		SHINING("shining", 16759902, StatusEffects.GLOWING, true),
		GLOOPY("gloopy", 9332621, (user, stack, world, teaType) -> Items.CHORUS_FRUIT.finishUsing(stack, world, user));

		private final String name;

		private final int color;
		private final int brewTime;
		private final TeaEffect effect;

		Flavor(String name, int color, TeaEffect effect) {
			this(name, color, effect, 200);
		}

		Flavor(String name, int color, TeaEffect effect, int brewTime) {
			this.name = name;
			this.brewTime = brewTime;
			this.color = color;
			this.effect = effect;
		}

		Flavor(String name, int color, StatusEffect effect, boolean add) {
			this(name, color, (user, stack, world, teaType) -> {
				if(effect != null) {
					if(add) {
						if(effect.isInstant()) {
							effect.applyInstantEffect(user, user, user, teaType.getStrength().getPotency(), 1.0D);
						}
						else {
							user.addStatusEffect(new StatusEffectInstance(effect, teaType.getStrength().getPotency() * 400));
						}
					}
					else {
						user.removeStatusEffect(effect);
					}
				}
			}, 200);
		}

		public static Flavor byName(String name) {
			for(Flavor flavor : Flavor.values()) {
				if(flavor.getName().equals(name)) {
					return flavor;
				}
			}
			return null;
		}

		public String getName() {
			return name;
		}

		public TeaEffect getEffect() {
			return effect;
		}

		public int getColor() {
			return color;
		}

		public int getBrewTime() {
			return brewTime;
		}
	}

	public enum Strength {
		WEAK("weak", 1),
		NORMAL("normal", 2),
		STRONG("strong", 3);

		private final String name;
		private final int potency;

		Strength(String name, int potency) {
			this.name = name;
			this.potency = potency;
		}

		public static Strength byName(String name) {
			for(Strength strength : Strength.values()) {
				if(strength.getName().equals(name)) {
					return strength;
				}
			}
			return null;
		}

		public static Strength byPotency(int potency) {
			for(Strength strength : Strength.values()) {
				if(strength.getPotency() == potency) {
					return strength;
				}
			}
			return null;
		}

		public String getName() {
			return name;
		}

		public int getPotency() {
			return potency;
		}
	}
}
