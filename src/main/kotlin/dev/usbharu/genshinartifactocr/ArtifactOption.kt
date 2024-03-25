package dev.usbharu.genshinartifactocr

import org.apache.lucene.search.spell.LevensteinDistance

sealed class ArtifactOption {
    abstract fun score(computeOptions: ComputeOptions): Float

    data class CriticalDamage(val percent: Float) : ArtifactOption() {
        override fun score(computeOptions: ComputeOptions): Float {
            return percent
        }
    }

    data class CriticalRate(val percent: Float) : ArtifactOption() {
        override fun score(computeOptions: ComputeOptions): Float {
            return percent * 2F
        }
    }

    data class Attack(val number: Int) : ArtifactOption() {
        override fun score(computeOptions: ComputeOptions): Float {
            return 0.0F
        }

    }

    data class AttackP(val percent: Float) : ArtifactOption() {
        override fun score(computeOptions: ComputeOptions): Float {
            return if (computeOptions == ComputeOptions.Attack) {
                percent
            } else {
                0F
            }
        }

    }

    data class Defence(val number: Int) : ArtifactOption() {
        override fun score(computeOptions: ComputeOptions): Float {
            return 0F
        }

    }

    data class DefenceP(val percent: Float) : ArtifactOption() {
        override fun score(computeOptions: ComputeOptions): Float {
            return if (computeOptions == ComputeOptions.Defense) {
                percent
            } else {
                0F
            }
        }

    }

    data class HP(val number: Int) : ArtifactOption() {
        override fun score(computeOptions: ComputeOptions): Float {
            return 0F
        }

    }

    data class HPP(val percent: Float) : ArtifactOption() {
        override fun score(computeOptions: ComputeOptions): Float {
            return if (computeOptions == ComputeOptions.HP) {
                percent
            } else {
                0F
            }
        }

    }

    data class ElementalMaster(val number: Int) : ArtifactOption() {
        override fun score(computeOptions: ComputeOptions): Float {
            return 0F
        }
    }

    data class EnergyRecharge(val percent: Float) : ArtifactOption() {
        override fun score(computeOptions: ComputeOptions): Float {
            return if (computeOptions == ComputeOptions.Charge) {
                percent
            } else {
                0F
            }
        }
    }
}