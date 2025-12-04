# Weather Forecast API App

This is a simple Android application that displays the current weather for selected cities in Portugal. It's built using modern Android development practices.

**Note:** This project was developed as an academic work. You can find the source code on [GitHub](https://github.com/BarreiroReSird/WeatherAPI).

## 🚀 Features

- **Current Weather:** View the current temperature and wind speed.
- **City Selection:** Choose from a list of Portuguese district capitals.
- **Data Caching:** Weather data is cached locally using a Room database, allowing for offline access to the last fetched data.
- **API Integration:** Fetches live weather data from the [Open-Meteo API](https://open-meteo.com/).
- **Modern UI:** The user interface is built entirely with Jetpack Compose.
- **Last Updated Timestamp:** Shows when the weather data was last successfully fetched from the API.

## 🛠️ Built With

- **[Kotlin](https://kotlinlang.org/):** The primary programming language.
- **[Jetpack Compose](https://developer.android.com/jetpack/compose):** For building the native UI.
- **[MVVM Architecture](https://developer.android.com/jetpack/guide):** For a clean and scalable app structure.
- **[Kotlin Coroutines & Flow](https://kotlinlang.org/docs/coroutines-guide.html):** For managing background threads and asynchronous data streams.
- **[Room](https://developer.android.com/jetpack/androidx/releases/room):** For local database storage and caching.
- **[OkHttp](https://square.github.io/okhttp/):** For making network requests to the weather API.
- **[Android Jetpack Libraries](https://developer.android.com/jetpack):** Including ViewModel, Navigation, and Lifecycle components.

## ⚙️ How to Build

1.  Clone this repository.
2.  Open the project in the latest version of Android Studio.
3.  Let Gradle sync the project dependencies.
4.  Build and run the `app` module on an Android emulator or a physical device.

---

# Aplicação de Previsão do Tempo (API)

Esta é uma aplicação Android simples que mostra a previsão do tempo atual para cidades selecionadas em Portugal. É construída com práticas modernas de desenvolvimento Android.

**Nota:** Este projeto foi desenvolvido como um trabalho académico. Pode encontrar o código-fonte no [GitHub](https://github.com/BarreiroReSird/WeatherAPI).

## 🚀 Funcionalidades

- **Tempo Atual:** Veja a temperatura e velocidade do vento atuais.
- **Seleção de Cidade:** Escolha de uma lista de capitais de distrito portuguesas.
- **Cache de Dados:** Os dados meteorológicos são guardados localmente numa base de dados Room, permitindo o acesso offline aos últimos dados obtidos.
- **Integração com API:** Obtém dados meteorológicos em tempo real da [API Open-Meteo](https://open-meteo.com/).
- **UI Moderna:** A interface do utilizador é construída inteiramente com Jetpack Compose.
- **Carimbo de Data/Hora da Última Atualização:** Mostra quando os dados meteorológicos foram obtidos com sucesso da API pela última vez.

## 🛠️ Construído Com

- **[Kotlin](https://kotlinlang.org/):** A linguagem de programação principal.
- **[Jetpack Compose](https://developer.android.com/jetpack/compose):** Para construir a UI nativa.
- **[Arquitetura MVVM](https://developer.android.com/jetpack/guide):** Para uma estrutura de aplicação limpa e escalável.
- **[Kotlin Coroutines & Flow](https://kotlinlang.org/docs/coroutines-guide.html):** Para gerir threads em segundo plano e fluxos de dados assíncronos.
- **[Room](https://developer.android.com/jetpack/androidx/releases/room):** Para armazenamento em base de dados local e cache.
- **[OkHttp](https://square.github.io/okhttp/):** Para fazer pedidos de rede à API de meteorologia.
- **[Bibliotecas Android Jetpack](https://developer.android.com/jetpack):** Incluindo componentes ViewModel, Navigation e Lifecycle.

## ⚙️ Como Compilar

1.  Clone este repositório.
2.  Abra o projeto na versão mais recente do Android Studio.
3.  Deixe o Gradle sincronizar as dependências do projeto.
4.  Compile e execute o módulo `app` num emulador Android ou num dispositivo físico.
