# Matic Kotlin SDK
[![](https://jitpack.io/v/maticnetwork/matic-kotlin.svg)](https://jitpack.io/#maticnetwork/matic-kotlin)


This repository contains the `matic` client library. `matic` makes it easy for developers, who may not be deeply familiar with smart contract development, to interact with the various components of Matic Network.

This library will help developers to move assets from Ethereum chain to Matic chain, and withdraw from Matic to Ethereum using fraud proofs.

We will be improving this library to make all features available like Plasma Faster Exit, challenge exit, finalize exit and more.

## Installation

  Add Jitpack to your project level build.gradle file
  
    allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
  Add the dependency in your app module's build.gradle file

	dependencies {
	        implementation 'com.github.maticnetwork:matic-kotlin:1.0.0'
	}

 That's it! <br> 
