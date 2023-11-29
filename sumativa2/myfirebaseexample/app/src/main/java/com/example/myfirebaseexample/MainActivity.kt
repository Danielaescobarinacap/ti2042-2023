package com.example.myfirebaseexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.example.myfirebaseexample.api.FirebaseApiAdapter
import com.example.myfirebaseexample.api.response.AyudantiaResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {
    // Referenciar campos de las interfaz
    private lateinit var idSpinner: Spinner
    private lateinit var capsuleField: EditText
    private lateinit var careerField: EditText
    private lateinit var assistantField: EditText
    private lateinit var buttonSave: Button
    private lateinit var buttonLoad: Button
    private lateinit var dateField: EditText

    // Referenciar la API
    private var firebaseApi = FirebaseApiAdapter()

    // Mantener los nombres e IDs de las armas
    private var ayudantiaList = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        idSpinner = findViewById(R.id.idSpinner)
        capsuleField = findViewById(R.id.capsuleField)
        careerField = findViewById(R.id.careerField)
        assistantField = findViewById(R.id.assistantField)

        buttonLoad = findViewById(R.id.buttonLoad)
        buttonLoad.setOnClickListener {
            Toast.makeText(this, "Cargando información", Toast.LENGTH_SHORT).show()
            runBlocking {
                getAyudantiaFromApi()
            }
        }

        buttonSave = findViewById(R.id.buttonSave)
        buttonSave.setOnClickListener {
            Toast.makeText(this, "Guardando información", Toast.LENGTH_SHORT).show()
            runBlocking {
                sendAyudantiaToApi()
            }
        }

        runBlocking {
            populateIdSpinner()
        }
    }

    private suspend fun populateIdSpinner() {
        val response = GlobalScope.async(Dispatchers.IO) {
            firebaseApi.getAyudantias()
        }
        val Ayudantias = response.await()
        Ayudantias?.forEach { key ,value->
            ayudantiaList.add("${key}: ${value.capsule}")
        }
        val ayudantiaAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, ayudantiaList)
        with(idSpinner) {
            adapter = ayudantiaAdapter
            setSelection(0, false)
            gravity = Gravity.CENTER
        }
    }

    private suspend fun getAyudantiaFromApi() {
        val selectedItem = idSpinner.selectedItem.toString()
        val ayudantiaId = selectedItem.subSequence(0, selectedItem.indexOf(":")).toString()
        println("Loading ${ayudantiaId}... ")
        val ayudantiaResponse = GlobalScope.async(Dispatchers.IO) {
            firebaseApi.getAyudantia(ayudantiaId)
        }
        val ayudantia = ayudantiaResponse.await()
        capsuleField.setText(ayudantia?.capsule)
        careerField.setText(ayudantia?.career)
        assistantField.setText(ayudantia?.assistant)
    }

    private suspend fun sendAyudantiaToApi() {
        val ayudantiaCapsule = capsuleField.text.toString()
        val career = careerField.text.toString()
        val assistant = assistantField.text.toString().toString()
        val date = dateField.text.toString()
        val ayudantia = AyudantiaResponse("",career, assistant, date)
        val ayudantiaResponse = GlobalScope.async(Dispatchers.IO) {
            firebaseApi.setAyudantia(ayudantia)
        }
        val response = ayudantiaResponse.await()
        capsuleField.setText(ayudantia?.capsule)
        careerField.setText(ayudantia?.career)
        assistantField.setText(ayudantia?.assistant)
    }
}
