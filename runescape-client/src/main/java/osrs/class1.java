package osrs;

import java.util.concurrent.Callable;
import net.runelite.mapping.Export;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("b")
public class class1 implements Callable {
	@ObfuscatedName("k")
	@ObfuscatedSignature(
		descriptor = "Lei;"
	)
	static ClanChannel field0;
	@ObfuscatedName("bz")
	static String field3;
	@ObfuscatedName("ee")
	@ObfuscatedSignature(
		descriptor = "Lkz;"
	)
	@Export("archive13")
	static Archive archive13;
	@ObfuscatedName("c")
	@ObfuscatedSignature(
		descriptor = "Lpi;"
	)
	final Buffer field2;
	@ObfuscatedName("b")
	@ObfuscatedSignature(
		descriptor = "Lm;"
	)
	final class3 field1;
	// $FF: synthetic field
	@ObfuscatedSignature(
		descriptor = "Lw;"
	)
	final class7 this$0;

	@ObfuscatedSignature(
		descriptor = "(Lw;Lpi;Lm;)V"
	)
	class1(class7 var1, Buffer var2, class3 var3) {
		this.this$0 = var1; // L: 47
		this.field2 = var2; // L: 48
		this.field1 = var3; // L: 49
	} // L: 50

	public Object call() {
		return this.field1.vmethod15(this.field2); // L: 54
	}

	@ObfuscatedName("p")
	@ObfuscatedSignature(
		descriptor = "(B)V",
		garbageValue = "42"
	)
	public static void method8() {
		VarbitComposition.VarbitDefinition_cached.clear(); // L: 43
	} // L: 44

	@ObfuscatedName("gw")
	@ObfuscatedSignature(
		descriptor = "(IIII)V",
		garbageValue = "2135260751"
	)
	static final void method12(int var0, int var1, int var2) {
		if (UserComparator7.cameraX < var0) { // L: 4548
			UserComparator7.cameraX = (var0 - UserComparator7.cameraX) * ItemComposition.field2012 / 1000 + UserComparator7.cameraX + Renderable.field2358; // L: 4549
			if (UserComparator7.cameraX > var0) { // L: 4550
				UserComparator7.cameraX = var0;
			}
		}

		if (UserComparator7.cameraX > var0) { // L: 4552
			UserComparator7.cameraX -= (UserComparator7.cameraX - var0) * ItemComposition.field2012 / 1000 + Renderable.field2358; // L: 4553
			if (UserComparator7.cameraX < var0) { // L: 4554
				UserComparator7.cameraX = var0;
			}
		}

		if (AbstractByteArrayCopier.cameraY < var1) { // L: 4556
			AbstractByteArrayCopier.cameraY = (var1 - AbstractByteArrayCopier.cameraY) * ItemComposition.field2012 / 1000 + AbstractByteArrayCopier.cameraY + Renderable.field2358; // L: 4557
			if (AbstractByteArrayCopier.cameraY > var1) { // L: 4558
				AbstractByteArrayCopier.cameraY = var1;
			}
		}

		if (AbstractByteArrayCopier.cameraY > var1) { // L: 4560
			AbstractByteArrayCopier.cameraY -= (AbstractByteArrayCopier.cameraY - var1) * ItemComposition.field2012 / 1000 + Renderable.field2358; // L: 4561
			if (AbstractByteArrayCopier.cameraY < var1) { // L: 4562
				AbstractByteArrayCopier.cameraY = var1;
			}
		}

		if (UserComparator10.cameraZ < var2) { // L: 4564
			UserComparator10.cameraZ = (var2 - UserComparator10.cameraZ) * ItemComposition.field2012 / 1000 + UserComparator10.cameraZ + Renderable.field2358; // L: 4565
			if (UserComparator10.cameraZ > var2) { // L: 4566
				UserComparator10.cameraZ = var2;
			}
		}

		if (UserComparator10.cameraZ > var2) { // L: 4568
			UserComparator10.cameraZ -= (UserComparator10.cameraZ - var2) * ItemComposition.field2012 / 1000 + Renderable.field2358; // L: 4569
			if (UserComparator10.cameraZ < var2) { // L: 4570
				UserComparator10.cameraZ = var2;
			}
		}

	} // L: 4572

	@ObfuscatedName("jg")
	@ObfuscatedSignature(
		descriptor = "(II)Ljava/lang/String;",
		garbageValue = "-1424242195"
	)
	static final String method11(int var0) {
		return var0 < 999999999 ? Integer.toString(var0) : "*"; // L: 11040 11041
	}
}
