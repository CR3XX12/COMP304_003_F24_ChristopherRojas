package com.group13.comp304sec003_lab04.data

import com.google.android.gms.maps.model.LatLng
import com.group13.comp304sec003_lab04.R
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Landmark(
    @SerialName("title") val title: String,
    @SerialName("subtitle") val subtitle: String,
    @SerialName("description") val description: String,
    @SerialName("image") val image: Int,
    @SerialName("lat") val lat: Double,
    @SerialName("lng") val lng: Double,
)

object LandmarkData {
    val historic = listOf(
        Landmark(
            title = "Chichen Itza",
            subtitle = "Yucatán, Mexico",
            description = "A renowned archaeological site located on the Yucatán Peninsula in Mexico. It was a major city of the Maya civilization during the Terminal Classic period. Known for its iconic pyramid, El Castillo, and other impressive structures, Chichen Itza is a UNESCO World Heritage Site and one of the New Seven Wonders of the World.",
            image = R.drawable.landmark_historic_chichen,
            lat = 20.6843,
            lng = -88.5678
        ),
        Landmark(
            title = "Teotihuacán",
            subtitle = "State of Mexico",
            description = "An ancient Mesoamerican city known for its vast size and iconic pyramids, the Pyramid of the Sun and the Pyramid of the Moon, central to religious and cultural activities.",
            image = R.drawable.landmark_historic_teotihuacan,
            lat = 19.6925,
            lng = -98.8438
        ),
        Landmark(
            title = "Tulum Ruins",
            subtitle = "Quintana Roo, Mexico",
            description = "A picturesque Maya site overlooking the Caribbean Sea, known for its well-preserved ruins and strategic coastal location.",
            image = R.drawable.landmark_historic_tulumruins,
            lat = 20.2140,
            lng = -87.4292
        ),
        Landmark(
            title = "Monte Albán",
            subtitle = "Oaxaca, Mexico",
            description = "An ancient Zapotec city set atop a hill, featuring plazas, pyramids, and tombs that reveal rich cultural and ceremonial practices.",
            image = R.drawable.landmark_historic_montealban,
            lat = 17.0439,
            lng = -96.7675
        ),
    )

    val park = listOf(
        Landmark(
            title = "Chapultepec Park",
            subtitle = "Mexico City",
            description = "One of the largest urban parks in the Western Hemisphere, home to a castle, museums, lakes, and extensive green spaces.",
            image = R.drawable.landmark_park_chapultepec,
            lat = 19.4204,
            lng = -99.1822
        ),
        Landmark(
            title = "Sumidero Canyon National Park",
            subtitle = "Chiapas, Mexico",
            description = "A dramatic canyon carved by the Grijalva River, offering boat tours with stunning views of towering cliffs and rich biodiversity.",
            image = R.drawable.landmark_park_sumiderocanyon,
            lat = 16.8466,
            lng = -93.0965
        ),
        Landmark(
            title = "Copper Canyon (Barranca del Cobre)",
            subtitle = "Chihuahua, Mexico",
            description = "A group of massive canyons larger and deeper than the Grand Canyon, known for its breathtaking views and Rarámuri indigenous culture.",
            image = R.drawable.landmark_park_coppercanyon,
            lat = 27.5215,
            lng = -107.9617
        ),
        Landmark(
            title = "Cenote Dos Ojos",
            subtitle = "Quintana Roo, Mexico",
            description = "A natural park centered around stunning cenotes, ideal for swimming, snorkeling, and cave exploration.",
            image = R.drawable.landmark_park_cenotedosojos,
            lat = 20.3051,
            lng = -87.3911
        ),
    )

    val museums = listOf(
        Landmark(
            title = "National Museum of Anthropology",
            subtitle = "Mexico City",
            description = "One of the most important museums in Mexico, showcasing the cultural heritage and artifacts of Mexico’s pre-Hispanic civilizations, including the Aztec Sun Stone.",
            image = R.drawable.landmark_museums_nationalanthropology,
            lat = 19.4260,
            lng = -99.1860
        ),
        Landmark(
            title = "Frida Kahlo Museum (Casa Azul)",
            subtitle = "Mexico City",
            description = "The former home of renowned artist Frida Kahlo, featuring her works, personal items, and insights into her life.",
            image = R.drawable.landmark_museums_fridakahlo,
            lat = 19.3554,
            lng = -99.1623
        ),
        Landmark(
            title = "Palacio de Bellas Artes",
            subtitle = "Mexico City",
            description = "A cultural landmark and performance space housing a museum with rotating exhibits of Mexican and international art.",
            image = R.drawable.landmark_museums_palacio,
            lat = 19.4352,
            lng = -99.1412
        ),
        Landmark(
            title = "Museum of Modern Art (MAM)",
            subtitle = "Mexico City",
            description = "A museum dedicated to modern Mexican art, showcasing works from artists like Diego Rivera and Rufino Tamayo.",
            image = R.drawable.landmark_museums_mam,
            lat = 19.4254,
            lng = -99.1862
        ),
    )

    val touristic = listOf(
        Landmark(
            title = "Cancun Beaches",
            subtitle = "Quintana Roo, Mexico",
            description = "Famous for their white sand, turquoise waters, and vibrant nightlife, Cancun is a top destination for relaxation and entertainment.",
            image = R.drawable.landmark_touristic_cancun,
            lat = 21.1619,
            lng = -86.8515
        ),
        Landmark(
            title = "Xcaret Eco-Archaeological Park",
            subtitle = "Playa del Carmen, Mexico",
            description = "A sprawling park offering cultural performances, natural attractions, and immersive experiences like underground rivers and wildlife exhibits.",
            image = R.drawable.landmark_touristic_xcaret,
            lat = 20.5786,
            lng = -87.1201
        ),
        Landmark(
            title = "Cabo San Lucas Arch (El Arco)",
            subtitle = "Baja California Sur, Mexico",
            description = "A stunning natural rock formation where the Pacific Ocean meets the Gulf of California, popular for boat tours and photography.",
            image = R.drawable.landmark_touristic_elarco,
            lat = 22.8751,
            lng = -109.8919
        ),
        Landmark(
            title = "Palenque",
            subtitle = "Chiapas, Mexico",
            description = "A lesser-known but stunning Maya site surrounded by jungle, featuring intricate carvings and temples like the Temple of the Inscriptions.",
            image = R.drawable.landmark_touristic_palenque,
            lat = 17.4843,
            lng = -92.0459
        ),
    )
}
