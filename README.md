<h1 align="center">Android Challenge Mercado Libre</h1></br>  
<p align="center">    
A demo MercadoLibre simple app using compose and Hilt based on modern Android tech-stacks and MVVM architecture.  

## Screenshots
<p align="center">  
<img src="https://github.com/MaldJuan94/ChallengeMercadoLibre/blob/main/preview/preview.gif?raw=true" width="32%"/>  
</p>  

## Tech stack & Open-source libraries
- Minimum SDK level 23
- 100% [Kotlin](https://kotlinlang.org/) based + [Coroutines](https://github.com/Kotlin/kotlinx.coroutines)
- Hilt for dependency injection.
- Kotlin DSL
- JetPack
  - Compose - A modern toolkit for building native Android UI.
  - Lifecycle - dispose observing data when lifecycle state changes.
  - ViewModel - UI related data holder, lifecycle aware.
- Architecture
  - MVVM Architecture (Declarative View - ViewModel - Model)
  - Repository and UsesCases
- Unit tests for packages:
  - Repository package
  - Use case package
  - ViewModel package