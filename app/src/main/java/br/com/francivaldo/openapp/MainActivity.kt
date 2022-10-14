package br.com.francivaldo.openapp

import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.francivaldo.openapp.ui.theme.OpenAppTheme


class MainActivity : ComponentActivity() {
    data class UiAppInfor(val packaName:String, val onClick:()->Unit)
    var listApps by mutableStateOf(mutableListOf<UiAppInfor>())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OpenAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ){
                        items(listApps){
                            Box(modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .clickable { it.onClick.invoke() },
                                contentAlignment = Alignment.Center
                            ){
                                Text(
                                    text = it.packaName,
                                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
        listApps = packageManager.getInstalledPackages(0).map { it.toUiAppInfor() }.toMutableList()

    }
    fun PackageInfo.toUiAppInfor():UiAppInfor = UiAppInfor(this.packageName,{
        try {
            this@MainActivity.startActivity(packageManager.getLaunchIntentForPackage(this.packageName))
        }catch (e:Exception){
            Toast.makeText(this@MainActivity,"nao foi pocivel abrir este app",Toast.LENGTH_LONG).show()
        }
    })
}