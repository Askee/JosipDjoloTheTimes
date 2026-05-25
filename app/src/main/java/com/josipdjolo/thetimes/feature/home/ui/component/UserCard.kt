package com.josipdjolo.thetimes.feature.home.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.josipdjolo.thetimes.core.domain.User
import com.josipdjolo.thetimes.core.ui.JosipPreview
import com.josipdjolo.thetimes.core.ui.ShimmerProvider
import com.josipdjolo.thetimes.core.ui.shimmer
import com.josipdjolo.thetimes.core.ui.theme.JosipTheme
import com.josipdjolo.thetimes.feature.home.UserData

@Composable
fun UserCard(user: UserData, onFollow: () -> Unit, onUnfollow: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.DarkGray, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(8.dp))
                .border(1.dp, Color.DarkGray, RoundedCornerShape(8.dp)),
            model = user.user.image,
            clipToBounds = true,
            contentDescription = null,
            error = rememberVectorPainter(Icons.Default.Person)
        )
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(user.user.name ?: "No name")
            Text(user.user.reputation.toString())
        }

        if (user.isFollowing)
            Button(
                onClick = onUnfollow,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            ) {
                Text("Unfollow")
            }
        else {
            Button(
                onClick = onFollow,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text("Follow")
            }
        }
    }
}

@Composable
fun UserCardLoading() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.DarkGray, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(8.dp))
                .shimmer(),
        )
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Box(
                modifier = Modifier
                    .width(100.dp)
                    .height(12.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .shimmer()
            )
            Box(
                modifier = Modifier
                    .width(80.dp)
                    .height(12.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .shimmer()
            )
        }
    }
}


@JosipPreview
@Composable
fun UserCardLoadingPreview() {
    JosipTheme {
        ShimmerProvider { UserCardLoading() }
    }
}

@JosipPreview
@Composable
fun UserCardPreview() {
    JosipTheme {
        ShimmerProvider {
            UserCard(
                UserData(
                    user = User(
                        1,
                        "",
                        name = "Name",
                        reputation = 123
                    ),
                    isFollowing = true
                ),
                onFollow = {},
                onUnfollow = {}
            )
        }
    }
}