# Czech Voice Agent

**CzechVoiceAgent** is an offline voice assistant written in Java that listens to your voice in Czech, sends recognized text to a local Ollama LLM model, receives a response, and speaks it back using espeak. The assistant cycles through angelic imagery and sunrise/sunset sky transitions for an immersive visual experience.

## Features

- Offline speech recognition using [Vosk](https://alphacephei.com/vosk/)
- LLM-based response via [Ollama](https://ollama.com/) (e.g., LLaMA3)
- Offline text-to-speech with `espeak`
- Czech language support end-to-end

##  Tech Stack

| Component        | Tool / Library         |
|------------------|------------------------|
| Speech-to-Text   | Vosk (Java binding)    |
| LLM Backend      | Ollama + Local model   |
| Text-to-Speech   | eSpeak      |
| JSON Processing  | org.json               |


## Requirements

- Java 11+
- Maven
- Ollama installed and running (`ollama run llama3`)
- `espeak` (installed and available in `PATH`)
- Vosk model for Czech (e.g. `vosk-model-small-cs-0.4-rhasspy`)

## Installation

### 1. Clone the repo

```bash
git clone https://github.com/AlexejDumka/CzechVoiceAgent.git
cd CzechVoiceAgent
