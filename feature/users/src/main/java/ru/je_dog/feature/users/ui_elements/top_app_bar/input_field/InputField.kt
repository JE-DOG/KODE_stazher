package ru.je_dog.feature.users.ui_elements.top_app_bar.input_field

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.je_dog.core.feature.R
import ru.je_dog.feature.users.vm.action.SortByBirthdayAction

@Composable
fun InputField(
    onTextChange: (String) -> Unit,
    text: String,
    onSortClick: () -> String,
    onCancelClick: () -> Unit
) {

    var hasFocus by remember {
        mutableStateOf(false)
    }
    var hasSorter by remember {
        mutableStateOf(false)
    }

    BasicTextField(
        modifier = Modifier
            .onFocusEvent {
                hasFocus = it.hasFocus
            },
        value = text,
        onValueChange = onTextChange,
        singleLine = true
    ) { inputField ->

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp)
                    .background(
                        Color(0xFFF7F7F8),
                        RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    painter = painterResource(
                        id = R.drawable.ic_loupe
                    ),
                    contentDescription = null,
                    tint = if (text.isEmpty() || hasFocus)
                        Color.Black
                    else
                        Color.Gray
                )

                Box(
                    Modifier
                        .padding(horizontal = 8.dp)
                ) {

                    if (!hasFocus){
                        Text(
                            text = stringResource(id = R.string.search_input_hint),
                            color = Color(0xFFC3C3C6),
                        )
                    }
                    inputField()

                }

                Spacer(modifier = Modifier.weight(1f))

                AnimatedVisibility(!hasFocus){

                    IconButton(
                        modifier = Modifier
                            .offset(x = 10.dp),
                        onClick = {
                            hasSorter = SortByBirthdayAction.SORT_NAME == onSortClick()
                        }
                    ){
                        Icon(
                            painter = painterResource(id = R.drawable.ic_list),
                            contentDescription = null,
                            tint = if(hasSorter)
                                Color.Red
                            else
                                Color.Gray
                        )
                    }

                }

            }

            AnimatedVisibility(visible = hasFocus){

                val focusManager = LocalFocusManager.current

                TextButton(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(horizontal = 8.dp),
                    onClick = {
                        onCancelClick()
                        focusManager.clearFocus()
                    }
                ){
                    Text(
                        text = "Отмена",
                        color = Color.Red
                    )
                }

            }

        }

    }

}

@Composable
@Preview(showBackground = true)
fun InputFieldPreview() {

    var text by remember {
        mutableStateOf("")
    }

    InputField(
        onTextChange = {
            text = it
        },
        text = text,
        onSortClick = {
            SortByBirthdayAction.SORT_NAME
        }
    ) {
        text = ""
    }

}