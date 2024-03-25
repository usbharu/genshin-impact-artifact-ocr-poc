package dev.usbharu.genshinartifactocr

import dev.usbharu.genshinartifactocr.ArtifactOption.*

class ComputeArtifactScore {
    fun compute(artifactOptions: List<ArtifactOption>, computeOptions: ComputeOptions): Float {

        var score: Float = 0F

        for (artifactOption in artifactOptions) {
            score += artifactOption.score(computeOptions)
        }
        return score
    }
}

enum class ComputeOptions {
    Attack,
    Defense,
    HP,
    Charge
}