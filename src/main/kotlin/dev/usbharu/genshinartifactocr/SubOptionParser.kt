package dev.usbharu.genshinartifactocr

import org.apache.lucene.search.spell.LevensteinDistance

class SubOptionParser {
    fun parse(string: String): ArtifactOption? {
        val split = string.split('+')

        val subOptionString = split.first()
        val subOptionValue = split.last().trim { !it.isDigit() && it != '%' }

        val listOf = listOf(
            "会心ダメージ",
            "会心率",
            "防御力",
            "攻撃力",
            "HP",
            "元素熟知",
            "元素チャージ"
        )

        val levensteinDistance = LevensteinDistance()
        val maxBy = listOf.maxBy { levensteinDistance.getDistance(it, subOptionString) }

        return when (maxBy) {
            "会心ダメージ" -> ArtifactOption.CriticalDamage(subOptionValue.trimEnd('%').toFloat())
            "会心率" -> ArtifactOption.CriticalRate(subOptionValue.trimEnd('%').toFloat())
            "防御力" -> if (subOptionValue.endsWith("%")) ArtifactOption.DefenceP(
                subOptionValue.trimEnd('%').toFloat()
            ) else ArtifactOption.Defence(subOptionValue.toInt())

            "攻撃力" -> if (subOptionValue.endsWith("%")) ArtifactOption.AttackP(
                subOptionValue.trimEnd('%').toFloat()
            ) else ArtifactOption.Attack(subOptionValue.toInt())

            "HP" -> if (subOptionValue.endsWith("%")) ArtifactOption.HPP(
                subOptionValue.trimEnd('%').toFloat()
            ) else ArtifactOption.HP(subOptionValue.toInt())

            "元素熟知" -> ArtifactOption.ElementalMaster(subOptionValue.toInt())
            "元素チャージ" -> ArtifactOption.EnergyRecharge(subOptionValue.trimEnd('%').toFloat())
            else -> null
        }
    }
}